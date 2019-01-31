package com.daggerdemo.mvp;

import com.daggerdemo.http.TaobaoLocationInfo;

/**
 * 功能：mvp  的 v
 * </p>
 * <p>Copyright corp.netease.com 2019 All right reserved </p>
 *
 * @author tuke 时间 2019/1/31
 * @email tuke@corp.netease.com
 * <p>
 * <p>
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
