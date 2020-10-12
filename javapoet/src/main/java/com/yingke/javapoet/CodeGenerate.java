package com.yingke.javapoet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class CodeGenerate {

    public static void main(String[] args) {
        MethodSpec methodSpec = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(int.class)
                .addParameter(String[].class, "args")
                .addStatement("int result = 0")
                .beginControlFlow("if (result == 0)")
                .addStatement("$T.out.println($S)", System.class, "hello JavaPoet")
                .endControlFlow()
                .addStatement("return result")
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addMethod(methodSpec)
                .build();

        JavaFile javaFile = JavaFile.builder("com.yingke.javapoet", typeSpec).build();
        try {
            javaFile.writeTo(System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
