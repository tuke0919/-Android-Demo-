package com.daggerdemo.app;

import com.daggerdemo.http.LocalRetrofit;
import com.daggerdemo.http.TaobaoRetrofit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * 功能：提供 Retrofit实例化的方法
 */
@Module
public class RetrofitModule {

    @Singleton
    @Provides
    public LocalRetrofit provideLocalRetrofit(@Named("default") OkHttpClient okHttpClient) {
        return new LocalRetrofit(okHttpClient);
    }

    @Singleton
    @Provides
    public TaobaoRetrofit provideTaobaoRetrofit(@Named("cache") OkHttpClient okHttpClient) {
        return new TaobaoRetrofit(okHttpClient);
    }

}
