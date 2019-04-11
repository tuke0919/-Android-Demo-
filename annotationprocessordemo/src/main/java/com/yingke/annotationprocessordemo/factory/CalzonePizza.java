package com.yingke.annotationprocessordemo.factory;

import com.yingke.annotation.Factory;

/**
 * 功能：
 * </p>
 * <p>Copyright corp.netease.com 2018 All right reserved </p>
 *
 * @author tuke 时间 2019/4/11
 * @email tuke@corp.netease.com
 * <p>
 * 最后修改人：无
 * <p>
 */
@Factory(
        id = "Calzone",
        type = Meal.class
)
public class CalzonePizza implements Meal {
    @Override
    public float getPrice() {
        return 8.5f;
    }

    @Override
    public String getInformation() {
        return MargheritaPizza.class.getSimpleName() + " is " + getPrice() + " dollars";
    }
}
