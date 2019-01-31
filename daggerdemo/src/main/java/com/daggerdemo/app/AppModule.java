package com.daggerdemo.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 功能：提供SharedPreferences和Context的方法
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }
    @Singleton
    @Provides
    public Context provideApplicationContext(){
        return context;
    }

    @Singleton
    @Provides
    @Named("default")
    public SharedPreferences getDefaultSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    @Named("filename")
    public SharedPreferences getFileNameSharedPrefereance() {
        return context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }
}
