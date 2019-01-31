package com.daggerdemo.app;

import com.daggerdemo.http.LocalRetrofit;
import com.daggerdemo.http.LocalUserService;
import com.daggerdemo.http.TaobaoLocationService;
import com.daggerdemo.http.TaobaoRetrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 功能：提供 Service 的实例化方法
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    public LocalUserService provideLocalUserService(LocalRetrofit localRetrofit){
        return localRetrofit.getRetrofit().create(LocalUserService.class);
    }

    @Singleton
    @Provides
    public TaobaoLocationService provideTaobaoLocationService(TaobaoRetrofit taobaoRetrofit) {
        return taobaoRetrofit.getRetrofit().create(TaobaoLocationService.class);
    }
}
