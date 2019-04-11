package com.yingke.annotation.compiler.module;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.yingke.annotation.compiler.exception.ProcessingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 功能：被Factory注解，且属于同一个工厂的类集合
 * </p>
 * <p>Copyright corp.netease.com 2018 All right reserved </p>
 *
 * @author tuke 时间 2019/4/10
 * @email tuke@corp.netease.com
 * <p>
 * 最后修改人：无
 * <p>
 */
public class FactoryGroupedClasses {

    private static final String SUFFIX = "Factory";

    // 全限定名，用来区分工厂
    private String mQualifiedClassName;
    // 集合 key = id value = 被注解的类信息
    private Map<String, FactoryAnnotatedClass> factoryAnnotatedClassMap = new LinkedHashMap<>();

    public FactoryGroupedClasses(String mQualifiedClassName) {
        this.mQualifiedClassName = mQualifiedClassName;
    }
    /**
     * 添加注解类型元素
     * @param annotatedClass
     */
    public void addItem(FactoryAnnotatedClass annotatedClass) {
        try {
            FactoryAnnotatedClass annotatedClass1 = factoryAnnotatedClassMap.get(annotatedClass.getId());
            if (annotatedClass1 != null) {
                throw new ProcessingException(annotatedClass1.getAnnotatedClassElement(), "id %s", annotatedClass1.getId());
            }
            factoryAnnotatedClassMap.put(annotatedClass.getId(), annotatedClass);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成代码
     * @param elementUtils
     * @param filer
     * @throws IOException
     */
    public void generateCode(Elements elementUtils, Filer filer) throws IOException {
        TypeElement superClassElement = elementUtils.getTypeElement(mQualifiedClassName);
        // 生成的工厂类，类名
        String factoryClassName = superClassElement.getSimpleName() + SUFFIX;
        // 父类的包元素
        PackageElement superClassPkgElement = elementUtils.getPackageOf(superClassElement);
        // 获取包名
        String packageName = superClassPkgElement.isUnnamed() ? null : superClassPkgElement.getQualifiedName().toString();
        // 生成方法
        MethodSpec.Builder method = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "id")
                .returns(TypeName.get(superClassElement.asType()));
        // 生成语句
        method.beginControlFlow("if (id == null)")
                .addStatement("throw new IllegalArgumentException($S)", "id is null!")
                .endControlFlow();
        // 生成每个item的语句
        for (FactoryAnnotatedClass annotatedClass : factoryAnnotatedClassMap.values()) {
            method.beginControlFlow("if ($S.equals(id))", annotatedClass.getId())
                    .addStatement("return new $L()", annotatedClass.getAnnotatedClassElement().getQualifiedName().toString())
                    .endControlFlow();
        }

        method.addStatement("throw new IllegalArgumentException($S + id)", "Unknown id = ");
        // 生成类
        TypeSpec typeSpec = TypeSpec.classBuilder(factoryClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(method.build())
                .build();
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }

}

