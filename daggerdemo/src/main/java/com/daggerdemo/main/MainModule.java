package com.daggerdemo.main;

import com.daggerdemo.mvp.IView;

import dagger.Module;
import dagger.Provides;

/**
 * 功能：提供 mvp的v
 */
@Module
public class MainModule {

    public IView iView;

    public MainModule(IView iView){
        this.iView = iView;
    }

    @ActivityScope
    @Provides
    public IView provideView() {
        return iView;
    }

}
