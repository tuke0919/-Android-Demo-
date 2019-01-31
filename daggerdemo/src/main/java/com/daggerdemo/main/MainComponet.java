package com.daggerdemo.main;

import com.daggerdemo.MainActivity;

import dagger.Component;
import dagger.Subcomponent;

/**
 * 功能：
 * </p>
 * <p>Copyright corp.netease.com 2019 All right reserved </p>
 *
 * @author tuke 时间 2019/1/31
 * @email tuke@corp.netease.com
 * <p>
 * <p>
 */
@ActivityScope
//很重要！这个Component应该是AppComponent的子Component，所以要使用这个注解
//不使用@Component注解的Dependents属性是因为希望能统一管理子Component
@Subcomponent(modules = MainModule.class)
public interface MainComponet {
    void inject(MainActivity activity);
}
