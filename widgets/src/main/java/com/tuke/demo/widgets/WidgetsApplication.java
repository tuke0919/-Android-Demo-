package com.tuke.demo.widgets;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * <p>
 * 功能：
 * </p>
 * <p>
 * 最后修改人：无
 */
public class WidgetsApplication extends Application {


    @Override
    public void onCreate () {
        super.onCreate();
        Fresco.initialize(this);
    }
}
