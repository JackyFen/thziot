package com.hnhz.thziot.api;

/**
 * Created by Zhou jiaqi on 2018/6/26.
 */




import com.hnhz.thziot.bean.login.AppAuth;
import com.hnhz.thziot.bean.login.AppAuthRequest;
import com.hnhz.thziot.bean.login.SaltBean;
import com.hnhz.thziot.bean.login.UserBean;
import com.kq.platform.kq_common.bean.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApiService {

    @GET("login/userLogin")
    Observable<HttpResult<UserBean>> login(@Query("code") String code);

    @GET("/authApi/auth/salt")
    Observable<HttpResult<SaltBean>> getSalt(@Query("client_id") String clientId, @Query("userLoginName") String userLoginName);

    @POST("/authApi/auth/authorizeApp")
    Observable<HttpResult<AppAuth>> appAuth(@Body AppAuthRequest appAuthRequest);

}
