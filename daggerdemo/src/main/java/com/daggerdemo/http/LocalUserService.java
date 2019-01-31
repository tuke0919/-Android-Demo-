package com.daggerdemo.http;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
public interface LocalUserService {
    @GET("/users")
    Observable<Response<List<LocalUser>>> getUsers();
    @GET("/users/{id}")
    Observable<Response<LocalUser>> getUser(@Path("id") String id);
}
