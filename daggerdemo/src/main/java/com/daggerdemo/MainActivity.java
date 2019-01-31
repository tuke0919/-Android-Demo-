package com.daggerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daggerdemo.app.AppComponet;
import com.daggerdemo.app.DaggerAppComponet;
import com.daggerdemo.http.TaobaoLocationInfo;
import com.daggerdemo.main.MainComponet;
import com.daggerdemo.main.MainModule;
import com.daggerdemo.mvp.IView;
import com.daggerdemo.mvp.MainPresenter;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * 参考博文：https://blog.csdn.net/lyglostangel/article/details/78685130
 *
 */
public class MainActivity extends AppCompatActivity implements IView {

    @Inject
    public MainPresenter mainPresenter;
    TextView textView;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        setupMainComponet();
        mainPresenter.getLocation();
    }

    /**
     * 设置 maincomponet
     */
    public void setupMainComponet() {
        AppComponet appComponet = DaggerApplication.get(this)
                .getAppComponet();
        MainComponet mainComponet = appComponet.addSub(new MainModule(this));
        mainComponet.inject(this);
    }


    public void initData(){


    }

    @Override
    public void showLocationInfo(TaobaoLocationInfo locationInfo) {
        if (locationInfo.getCode() == 0) {
            Log.e("location","ip: " + locationInfo.getData().getIp());
            Log.e("location","country: " + locationInfo.getData().getCountry());
            Log.e("location","area: " + locationInfo.getData().getArea());
            Log.e("location","city: " + locationInfo.getData().getCity());
            Log.e("location","isp: " + locationInfo.getData().getIsp());
        } else {
            Log.e("location","code: " + locationInfo.getCode());
        }

    }

    @Override
    public void showError(String mesage) {
        Log.e("location",mesage);
    }
}
