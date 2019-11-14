package com.hnhz.thziot.global;

/**
 * Created by Zhou jiaqi on 2019/6/10.
 */
public class GlobalData {

    private static String appEntranceUrl;


    public static String getAppEntranceUrl() {
        return appEntranceUrl;
    }

    public static void setAppEntranceUrl(String appEntranceUrl) {
        GlobalData.appEntranceUrl = appEntranceUrl;
    }
}
