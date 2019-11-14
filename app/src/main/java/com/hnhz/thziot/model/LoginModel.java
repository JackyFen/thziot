package com.hnhz.thziot.model;




import com.hnhz.thziot.bean.login.UserBean;
import com.kq.platform.kq_common.bean.HttpResult;

import io.reactivex.Observable;


/**
 * Created by Zhou jiaqi on 2018/6/26.
 */

public interface LoginModel {
    Observable<HttpResult<UserBean>> login(String userName, String pwd);

    Observable<HttpResult<UserBean>> autoLogin(String userName, String salt);
}
