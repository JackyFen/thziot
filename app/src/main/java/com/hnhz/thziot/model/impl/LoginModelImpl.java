package com.hnhz.thziot.model.impl;

import com.hnhz.thziot.bean.login.SaltBean;
import com.hnhz.thziot.bean.login.UserBean;
import com.hnhz.thziot.global.Constant;
import com.hnhz.thziot.http.ApiManager;
import com.hnhz.thziot.model.LoginModel;
import com.kq.platform.kq_common.bean.HttpResult;
import com.kq.platform.kq_common.http.CustomHttpException;
import com.kq.platform.kq_common.utils.MD5;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhou jiaqi on 2018/6/26.
 */

public class LoginModelImpl implements LoginModel {

    @Override
    public Observable<HttpResult<UserBean>> login(String userName, String pwd) {
        Observable<HttpResult<SaltBean>> getSaltOb = ApiManager.getSalt(Constant.CLIENT_ID,userName);

        return getSaltOb.flatMap(saltBeanHttpResult -> {
            if (saltBeanHttpResult.getServerResult() == null) {
                return Observable.error(new CustomHttpException("网络链接错误","999"));
            } else if(!saltBeanHttpResult.getServerResult().getResultCode().equals("0")){
                return Observable.error(new CustomHttpException(saltBeanHttpResult.getServerResult().getResultMsg(),
                        saltBeanHttpResult.getServerResult().getResultCode()));
            } else {
                SaltBean saltBean = saltBeanHttpResult.getResultVo();
                String salt = saltBean.getSalt();
                String time = saltBean.getTime();
                String cypherPwd = MD5.getMD5ofStr(MD5.getMD5ofStr(MD5.getMD5ofStr(pwd) + salt) + time);
                return ApiManager.appAuth(Constant.CLIENT_ID, "code", userName, cypherPwd);
            }
        }).flatMap(appAuthHttpResult -> {
            if (appAuthHttpResult.getServerResult() == null) {
                return Observable.error(new CustomHttpException("网络链接错误","999"));
            } else if(!appAuthHttpResult.getServerResult().getResultCode().equals("0")){
                return Observable.error(new CustomHttpException(appAuthHttpResult.getServerResult().getResultMsg(),
                        appAuthHttpResult.getServerResult().getResultCode()));
            } else {
                String code = appAuthHttpResult.getResultVo().getCode();
                return ApiManager.login(code);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpResult<UserBean>> autoLogin(String userName, String salt) {
        Observable<HttpResult<SaltBean>> getSaltOb = ApiManager.getSalt(Constant.CLIENT_ID,userName);

        return getSaltOb.flatMap(saltBeanHttpResult -> {
            if (saltBeanHttpResult.getServerResult() == null) {
                return Observable.error(new CustomHttpException("网络链接错误","999"));
            } else if(!saltBeanHttpResult.getServerResult().getResultCode().equals("0")){
                return Observable.error(new CustomHttpException(saltBeanHttpResult.getServerResult().getResultMsg(),
                        saltBeanHttpResult.getServerResult().getResultCode()));
            } else {
                SaltBean saltBean = saltBeanHttpResult.getResultVo();
                String time = saltBean.getTime();
                String cypherPwd = MD5.getMD5ofStr(salt + time);
                return ApiManager.appAuth(Constant.CLIENT_ID, "code", userName, cypherPwd);
            }
        }).flatMap(appAuthHttpResult -> {
            if (appAuthHttpResult.getServerResult() == null) {
                return Observable.error(new CustomHttpException("网络链接错误","999"));
            } else if(!appAuthHttpResult.getServerResult().getResultCode().equals("0")){
                return Observable.error(new CustomHttpException(appAuthHttpResult.getServerResult().getResultMsg(),
                        appAuthHttpResult.getServerResult().getResultCode()));
            } else {
                String code = appAuthHttpResult.getResultVo().getCode();
                return ApiManager.login(code);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
