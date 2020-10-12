package com.yingke.javaasmdemo;

/**
 * 功能：
 * </p>
 * <p>Copyright xxx.xxx.com 2020 All right reserved </p>
 *
 * @author tuke 时间 2020-10-12
 * @email <p>
 * 最后修改人：无
 * <p>
 */
public class AopInterceptor {

    public static void beforeInvoke() {
        System.out.println("before");
    }

    public static void afterInvoke() {
        System.out.println("after");
    }

}
