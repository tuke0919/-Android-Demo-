package com.daggerdemo.mvp;

import android.content.SharedPreferences;

import com.daggerdemo.http.LocalUserService;
import com.daggerdemo.http.TaobaoLocationInfo;
import com.daggerdemo.http.TaobaoLocationService;
import com.daggerdemo.main.MainModule;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * 功能：mvp的p
 */
public class MainPresenter {

    public MainModel mainModel;
    public IView iView;

    @Inject
    public MainPresenter(@Named("default") SharedPreferences sharedPreferences,
                         TaobaoLocationService locationService,
                         LocalUserService userService,
                         IView iView) {
        this.iView = iView;
        mainModel = new MainModel(sharedPreferences, locationService ,userService);
        mainModel.setListener(new MainModel.OnActionListener() {
            @Override
            public void showLocationInfo(TaobaoLocationInfo locationInfo) {
                MainPresenter.this.iView.showLocationInfo(locationInfo);
            }

            @Override
            public void showError(String mesage) {
                MainPresenter.this.iView.showError(mesage);
            }
        });
    }

    public void getLocation() {
        if (mainModel != null) {
            mainModel.getLocation();
        }
    }

}
