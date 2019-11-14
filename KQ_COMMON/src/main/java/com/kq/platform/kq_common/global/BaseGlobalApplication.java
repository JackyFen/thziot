package com.kq.platform.kq_common.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**
 * Created by Zhou jiaqi on 2019/8/21.
 */
public class BaseGlobalApplication extends Application {

    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;
    protected static BaseGlobalApplication mApp;

    public static synchronized BaseGlobalApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
//
//        String token = new PrefsUtils(this).get("token","");
//        LoginData.setToken(token);

    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }



}
