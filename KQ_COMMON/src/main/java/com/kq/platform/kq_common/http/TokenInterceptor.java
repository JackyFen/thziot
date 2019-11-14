package com.kq.platform.kq_common.http;

import com.kq.platform.kq_common.global.BaseConstant;
import com.kq.platform.kq_common.global.BaseGlobalApplication;
import com.kq.platform.kq_common.utils.PrefsUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 封装公共参数
 * Created by Zhou jiaqi on 2018/3/12.
 */

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request oldRequest = chain.request();


        PrefsUtils prefsUtils = new PrefsUtils(BaseGlobalApplication.getContext(), BaseConstant.PREF_TOKEN_FILE);

        String jsession = prefsUtils.get(BaseConstant.PREF_KEY_JSESSIONID,"");
        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter(BaseConstant.PREF_KEY_JSESSIONID,jsession);

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .header("x-auth-token",jsession)
               // .addHeader("cookie","JSESSIONID="+jsession)
                .build();

        return chain.proceed(newRequest);
    }
}