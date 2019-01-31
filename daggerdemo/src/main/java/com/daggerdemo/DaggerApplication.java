package com.daggerdemo;

import android.app.Application;
import android.content.Context;

import com.daggerdemo.app.AppComponet;
import com.daggerdemo.app.AppModule;
import com.daggerdemo.app.DaggerAppComponet;

/**
 * 功能：实例化AppComponet
 */
public class DaggerApplication extends Application {

    public static AppComponet appComponet;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static DaggerApplication get(Context context) {
        return (DaggerApplication) context.getApplicationContext();
    }

    public  AppComponet getAppComponet() {
        if (appComponet == null) {
            setupAppComponet();
        }
        return appComponet;
    }
    public void  setupAppComponet() {
        appComponet = DaggerAppComponet.builder()
                                       .appModule(new AppModule(this))
                                       .build();
    }
}
