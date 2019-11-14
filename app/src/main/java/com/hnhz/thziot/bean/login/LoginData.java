package com.hnhz.thziot.bean.login;

/**
 * Created by Zhou jiaqi on 2018/6/26.
 */

public class LoginData {

    private static UserBean globalUserBean;


    public static UserBean getGlobalUserBean() {
        return globalUserBean;
    }

    public static void setGlobalUserBean(UserBean globalUserBean) {
        LoginData.globalUserBean = globalUserBean;
    }

}
