package com.daggerdemo.mvp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.daggerdemo.http.LocalUserService;
import com.daggerdemo.http.TaobaoLocationInfo;
import com.daggerdemo.http.TaobaoLocationService;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Named;

import io.reactivex.Observer;
import io.reactivex.Scheduler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



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
public class MainModel {

    private final String IP = "220.181.102.176";
    private final SharedPreferences sharedPreferences;
    private final TaobaoLocationService locationService;
    private final LocalUserService userService;
    // 回调
    public OnActionListener listener;

    public MainModel(@Named("default")SharedPreferences sharedPreferences,
                     TaobaoLocationService locationService,
                     LocalUserService userService) {

        this.sharedPreferences = sharedPreferences;
        this.locationService = locationService;
        this.userService = userService;
    }

    @SuppressLint("CheckResult")
    public void getLocation() {
        if (locationService != null) {
            locationService.getInfo(IP)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<TaobaoLocationInfo>() {


                        @Override
                        public void onError(Throwable e) {
                              if (listener != null) {
                                  listener.showError(e.getMessage());
                              }
                        }

                        @Override
                        public void onComplete() {

                        }
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(TaobaoLocationInfo locationInfo) {
                             if (listener != null) {
                                 listener.showLocationInfo(locationInfo);
                             }
                        }
                    });
        }

    }

    /**
     * 设置 监听器
     * @param listener
     */
    public void setListener(OnActionListener listener) {
        this.listener = listener;
    }

    public interface OnActionListener {
        /**
         * 显示 位置信息
         * @param locationInfo
         */
        void showLocationInfo(TaobaoLocationInfo locationInfo);

        /**
         * 显示 错误信息
         * @param mesage
         */
        void showError(String mesage);
    }
}
