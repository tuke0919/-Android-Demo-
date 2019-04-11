package com.yingke.annotation.compiler.module;

import com.yingke.annotation.Factory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * 功能：被Factory注解的类信息
 * </p>
 * <p>Copyright corp.netease.com 2018 All right reserved </p>
 *
 * @author tuke 时间 2019/4/10
 * @email tuke@corp.netease.com
 * <p>
 * 最后修改人：无
 * <p>
 */
public class FactoryAnnotatedClass {
    // 被注解的类元素
    private TypeElement mAnnotatedClassElement;
    // 包名.类名的 全限定名
    private String mQualifiedSuperClassName;
    // 此类型元素的简单名称
    private String mSimpleTypeName;
    // 唯一id
    private String mId;

    public FactoryAnnotatedClass(TypeElement classElement) {
        mAnnotatedClassElement = classElement;

        // 类元素的注解
        Factory annotation = classElement.getAnnotation(Factory.class);
        // 注解的id
        mId = annotation.id();
        // 检查
        if (mId == null || mId.length() == 0) {
            throw new IllegalArgumentException(
                    String.format("id() in @%s for class %s is null or empty! that's not allowed",
                            Factory.class.getSimpleName(), classElement.getQualifiedName().toString()));
        }

        try {
            // 注解中定义的type
           Class<?> clazz = annotation.type();
           // 父类 全限定名，就是包名.类型
           mQualifiedSuperClassName = clazz.getCanonicalName();
           // 父类，简单类型
           mSimpleTypeName = clazz.getSimpleName();

        } catch (MirroredTypeException e) {
            DeclaredType classTypeMirror = (DeclaredType) e.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            mQualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
            mSimpleTypeName = classTypeElement.getSimpleName().toString();
        }

    }

    /**
     * 被注解的类元素
     * @return
     */
    public TypeElement getAnnotatedClassElement() {
        return mAnnotatedClassElement;
    }

    /**
     * 父类 全限定名，就是包名.类型
     * @return
     */
    public String getQualifiedSuperClassName() {
        return mQualifiedSuperClassName;
    }

    /**
     * 父类，简单类型
     * @return
     */
    public String getSimpleTypeName() {
        return mSimpleTypeName;
    }

    /**
     * 唯一id
     * @return
     */
    public String getId() {
        return mId;
    }
}
