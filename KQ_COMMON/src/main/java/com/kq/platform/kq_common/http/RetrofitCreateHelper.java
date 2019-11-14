package com.kq.platform.kq_common.http;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class RetrofitCreateHelper {
    /**
     * 设置默认超时
     */
    private static final int DEFAULT_TIMEOUT = 40;

//    private static CookieHandler cookieHandler = new CookieManager(
//            new PersistentCookieStore(GlobalApplication.getContext()), CookiePolicy.ACCEPT_ALL);

    private static OkHttpClient commonClientBuilder(int timeout, boolean hasToken) {
        //手动创建一个OkHttpClient并设置超时时间
        TokenInterceptor tokenInterceptor = new TokenInterceptor();

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(timeout, TimeUnit.SECONDS);


        if(hasToken){
            builder.addInterceptor(tokenInterceptor);
        }


       // builder.cookieJar(new JavaNetCookieJar(cookieHandler));

//        if(hasToken){
//
//            builder.cookieJar(new CookieJar() {
//            @Override
//            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//            }
//
//            @Override
//            public List<Cookie> loadForRequest(HttpUrl url) {
//                    PrefsUtils prefsUtils = new PrefsUtils(GlobalApplication.getContext(), BaseConstant.PREF_TOKEN_FILE);
//                    String jsession = prefsUtils.get("jsessionId","");
//                    String clientinfo = prefsUtils.get("clientinfo","");
//
//                    Cookie.Builder builder1 = new Cookie.Builder()
//                            .name("JSESSIONID")
//                            .value(jsession)
//                            .domain(url.host());
//
//                    Cookie jSessionIdCookie = builder1.build();
//
//                    Cookie.Builder  builder2 = new Cookie.Builder()
//                            .name("clientinfo")
//                            .value(clientinfo)
//                            .domain(url.host());
//
//                    Cookie clientInfoCookie = builder2.build();
//
//                    List<Cookie> cookies = new ArrayList<>();
//                    cookies.add(jSessionIdCookie);
//                    cookies.add(clientInfoCookie);
//                    return cookies;
//
//                }
//            });
//        }
        return builder.build();
    }

    public static <T> T createApi(Class<T> clazz, String url) {
        return createApi(clazz, url, DEFAULT_TIMEOUT,true);
    }

    public static <T> T createApi(Class<T> clazz, String url,boolean hasToken) {
        return createApi(clazz, url, DEFAULT_TIMEOUT,hasToken);
    }

    public static <T> T createApi(Class<T> clazz, String url, int timeout,boolean hasToken) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(commonClientBuilder(timeout,hasToken))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(clazz);
    }


}