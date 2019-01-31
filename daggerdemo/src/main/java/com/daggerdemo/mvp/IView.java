package com.daggerdemo.mvp;

import com.daggerdemo.http.TaobaoLocationInfo;

/**
 * 功能：mvp  的 v
 */
public interface IView {

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
