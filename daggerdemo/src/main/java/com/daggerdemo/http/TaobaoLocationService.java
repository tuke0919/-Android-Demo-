package com.daggerdemo.http;



import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
public interface TaobaoLocationService {

    @GET("/service/getIpInfo2.php")
    Observable<TaobaoLocationInfo> getInfo(@Query("ip") String ip);
}
