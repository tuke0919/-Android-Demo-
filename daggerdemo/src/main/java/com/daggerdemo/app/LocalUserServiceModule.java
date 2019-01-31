package com.daggerdemo.app;

import com.daggerdemo.http.LocalRetrofit;
import com.daggerdemo.http.LocalUserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
@Module
public class LocalUserServiceModule {

    @Singleton
    @Provides
    public LocalUserService provideLocalUserService(LocalRetrofit localRetrofit){
        return localRetrofit.getRetrofit().create(LocalUserService.class);
    }
}
