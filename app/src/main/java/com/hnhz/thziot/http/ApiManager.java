package com.hnhz.thziot.http;


import com.hnhz.thziot.api.LoginApiService;
import com.hnhz.thziot.api.ProductApiService;
import com.hnhz.thziot.bean.MenuBean;
import com.hnhz.thziot.bean.MenuRightBean;
import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.bean.SubItemBean;
import com.hnhz.thziot.bean.login.AppAuth;
import com.hnhz.thziot.bean.login.AppAuthRequest;
import com.hnhz.thziot.bean.login.SaltBean;
import com.hnhz.thziot.bean.login.UserBean;
import com.kq.platform.kq_common.bean.HttpResult;
import com.kq.platform.kq_common.http.RetrofitCreateHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhou jiaqi on 2019/1/24.
 */
public class ApiManager {

    public static final String AUTH_URL = URLManager.AUTH_URL;

    //public static final String BUSINESS_URL = URLManager.BUSINESS_URL + URLManager.BUSINESS_PORT + URLManager.SMOKE_DETECTOR_PATH;

    public static final String PLATFORM_URL = URLManager.PLATFORM_URL + URLManager.PLATFORM_PORT + URLManager.PLATFORM_PATH;

    /**
     * 登录(code)
     */
    public static Observable<HttpResult<UserBean>> login(String code) {
        return RetrofitCreateHelper.createApi(LoginApiService.class, PLATFORM_URL, false)
                .login(code);
    }

    /**
     * 获取salt
     *
     * @param clientId 固定clientId
     * @param userName 用户登录名
     */
    public static Observable<HttpResult<SaltBean>> getSalt(String clientId, String userName) {
        return RetrofitCreateHelper.createApi(LoginApiService.class, AUTH_URL, false)
                .getSalt(clientId, userName);
    }

    /**
     * App认证
     *
     * @param clientId     固定ClientId
     * @param responseType 回复类型（默认为code）
     * @param userName     用户登录名
     * @param password     用户登录密码(((Salt:md5):md5+time):md5)
     */
    public static Observable<HttpResult<AppAuth>> appAuth(String clientId, String responseType, String userName, String password) {
        AppAuthRequest appAuthRequest = new AppAuthRequest(clientId, responseType, userName, password);
        return RetrofitCreateHelper.createApi(LoginApiService.class, AUTH_URL, false)
                .appAuth(appAuthRequest);
    }

    public static Observable<HttpResult<List<ProductBean>>> getAllProduct(){
        return RetrofitCreateHelper.createApi(ProductApiService.class,PLATFORM_URL).getAllProductInfo();
    }


    public static Observable<HttpResult<List<SubItemBean>>> getAllSubItems(Long productId){
        return RetrofitCreateHelper.createApi(ProductApiService.class,PLATFORM_URL).getAllSubItems(productId);
    }


    public static Observable<HttpResult<MenuRightBean>> getMenuInfo(Long subItemsId, Long productId){
        return RetrofitCreateHelper.createApi(ProductApiService.class,PLATFORM_URL).getMenuInfo(subItemsId,productId);
    }
}
