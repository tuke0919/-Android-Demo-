package com.yingke.annotation.compiler.processor;

import com.google.auto.service.AutoService;
import com.yingke.annotation.Factory;
import com.yingke.annotation.compiler.exception.ProcessingException;
import com.yingke.annotation.compiler.module.FactoryAnnotatedClass;
import com.yingke.annotation.compiler.module.FactoryGroupedClasses;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * 功能：@Factory 注解处理类
 * </p>
 * <p>Copyright corp.netease.com 2018 All right reserved </p>
 *
 * @author tuke 时间 2019/4/10
 * @email tuke@corp.netease.com
 * <p>
 * 最后修改人：无
 * <p>
 */
@AutoService(Processor.class)
public class FactoryProcessor extends AbstractProcessor {
    // 原始类型工具
    private Types mTypeUtils;
    // 日志相关的辅助类
    private Messager mMessager;
    // 文件过滤器
    private Filer mFiler;
    // 程序元素工具
    private Elements mElementsUtils;

    // 工厂集合 key = 父类全限定名，value = 相同工厂的注解类结合
    private Map<String, FactoryGroupedClasses> factoryGroupedClassesMap = new LinkedHashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mTypeUtils = processingEnvironment.getTypeUtils();
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
        mElementsUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        try {
            // 1. 遍历所有被@Factory注解的程序元素
            for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Factory.class)) {
                // 2. 检查被注解为@Factory的元素是否是一个类
                if (annotatedElement.getKind() != ElementKind.CLASS) {
                    throw new ProcessingException(annotatedElement, "Only classes can be annotated with @%s", Factory.class.getSimpleName());
                }
                // 3. 因为我们已经知道它是ElementKind.CLASS类型，所以可以直接强制转换
                TypeElement typeElement = (TypeElement) annotatedElement;
                // 4. 类元素的类信息
                FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(typeElement);
                // 5. 检查类元素 的有效性
                checkValidClass(annotatedClass);
                // 6. 所有检查都没有问题，所以可以添加了
                FactoryGroupedClasses factoryGroupedClasses = factoryGroupedClassesMap.get(annotatedClass.getQualifiedSuperClassName());
                if (factoryGroupedClasses == null) {
                    String qualifiedGroupName = annotatedClass.getQualifiedSuperClassName();
                    factoryGroupedClasses = new FactoryGroupedClasses(qualifiedGroupName);
                    factoryGroupedClassesMap.put(qualifiedGroupName, factoryGroupedClasses);
                }
                // 如果和其他的@Factory标注的类的id相同冲突，会抛出异常
                factoryGroupedClasses.addItem(annotatedClass);
            }

            // 7. 产生代码
            for (FactoryGroupedClasses groupedClasses : factoryGroupedClassesMap.values()) {
                groupedClasses.generateCode(mElementsUtils, mFiler);
            }
            // 8. 清除factoryClasses
            factoryGroupedClassesMap.clear();
        } catch (ProcessingException e) {
            // error 错误
            error(e.getElement(), e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * error 错误
     * @param element
     * @param msg
     * @param args
     */
    private void error(Element element, String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    /**
     * error 错误
     * @param msg
     * @param args
     */
    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    /**
     * info 信息
     * @param msg
     * @param args
     */
    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }

    /**
     * 检查 被注解类的有效性
     * @param annotatedClass
     * @throws ProcessingException
     */
    private void checkValidClass(FactoryAnnotatedClass annotatedClass) throws ProcessingException {

        TypeElement classElement = annotatedClass.getAnnotatedClassElement();
        // 1.必须是公开类
        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            throw new ProcessingException(classElement, "The class %s is not public.", classElement.getQualifiedName().toString());
        }
        // 2. 必须是非抽象类
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new ProcessingException(classElement,
                    "The class %s is abstract. You can't annotate abstract classes with @%",
                    classElement.getQualifiedName().toString(), Factory.class.getSimpleName());
        }
        // 3. 必须是@Factoy.type()指定的类型的子类或者接口的实现
        TypeElement superClassElement = mElementsUtils.getTypeElement(annotatedClass.getQualifiedSuperClassName());

        if (superClassElement.getKind() == ElementKind.INTERFACE) {
            if (!classElement.getInterfaces().contains(superClassElement.asType())) {
                throw new ProcessingException(classElement,
                        "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        annotatedClass.getQualifiedSuperClassName());
            }
        } else {
            // 当前类元素
            TypeElement currentClassElement = classElement;
            while (true) {
                // 得到父类的原始类型
                TypeMirror superClassType = currentClassElement.getSuperclass();
                if (superClassType.getKind() == TypeKind.NONE) {
                    // 如果已经到达了 java.lang.Object，所以退出
                    throw new ProcessingException(classElement,
                            "The class %s annotated with @%s must inherit from %s",
                            classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                            annotatedClass.getQualifiedSuperClassName());
                }
                if (superClassType.toString().equals(annotatedClass.getQualifiedSuperClassName())) {
                    break;
                }
                // 原始类型 转换为 类型元素
                currentClassElement = (TypeElement) mTypeUtils.asElement(superClassType);
            }
        }
        // 4. 类必须有一个公开的默认构造函数
        for (Element childElement : classElement.getEnclosedElements()) {
            if (childElement.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElemnt = (ExecutableElement) childElement;
                if (constructorElemnt.getParameters().size() == 0 &&
                        constructorElemnt.getModifiers().contains(Modifier.PUBLIC)) {
                    return;
                }
            }
        }

        // No empty constructor found
        throw new ProcessingException(classElement,
                "The class %s must provide an public empty default constructor",
                classElement.getQualifiedName().toString());

    }

    /**
     * 必须指定：返回支持注解的类型，可以是很多,目前只有Factory
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        info("CanonicalName is %s", Factory.class.getCanonicalName());
        annotationTypes.add(Factory.class.getCanonicalName());
        return annotationTypes;
    }

    /**
     * 指定你使用的Java版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
