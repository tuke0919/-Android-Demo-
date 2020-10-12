package com.yingke.javaasmdemo;

public class TestBeanTarget {

    public void helloAop(){
        AopInterceptor.beforeInvoke();
        System.out.println("hello aop");
        AopInterceptor.afterInvoke();
    }

}
