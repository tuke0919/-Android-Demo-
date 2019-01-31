package com.daggerdemo.app;

import com.daggerdemo.http.TaobaoLocationService;
import com.daggerdemo.http.TaobaoRetrofit;

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
public class TaobaoLocationServiceModule {
    @Singleton
    @Provides
    public TaobaoLocationService provideTaobaoLocationService(TaobaoRetrofit taobaoRetrofit) {
        return taobaoRetrofit.getRetrofit().create(TaobaoLocationService.class);
    }
}
