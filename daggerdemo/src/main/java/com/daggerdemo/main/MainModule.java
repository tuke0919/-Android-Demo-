package com.daggerdemo.main;

import com.daggerdemo.mvp.IView;

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
