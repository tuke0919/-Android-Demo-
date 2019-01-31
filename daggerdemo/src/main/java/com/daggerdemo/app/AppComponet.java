package com.daggerdemo.app;

import com.daggerdemo.app.AppModule;
import com.daggerdemo.app.OkHttpClientModule;
import com.daggerdemo.app.RetrofitModule;
import com.daggerdemo.app.ServiceModule;
import com.daggerdemo.main.MainComponet;
import com.daggerdemo.main.MainModule;

import javax.inject.Singleton;

import dagger.Component;

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
@Singleton
@Component(modules = {
        AppModule.class,
        OkHttpClientModule.class,
        RetrofitModule.class,
        ServiceModule.class,})
public interface AppComponet {
    MainComponet addSub(MainModule mainModule);
}
