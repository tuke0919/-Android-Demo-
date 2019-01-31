package com.daggerdemo.http;

import javax.inject.Named;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * 功能：taobao的Retrofit
 */
public class TaobaoRetrofit {

    private static final String BASE_URL = "http://ip.taobao.com/";
    private static Retrofit retrofit;

    public TaobaoRetrofit(OkHttpClient okHttpClient) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();

    }

    public  Retrofit getRetrofit() {
        return retrofit;
    }
}
