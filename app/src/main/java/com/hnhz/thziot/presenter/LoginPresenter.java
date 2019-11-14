package com.hnhz.thziot.presenter;


import com.hnhz.thziot.bean.login.UserBean;
import com.hnhz.thziot.model.LoginModel;
import com.hnhz.thziot.model.impl.LoginModelImpl;
import com.hnhz.thziot.presenter.view.ILoginView;
import com.kq.platform.kq_common.bean.HttpResult;
import com.kq.platform.kq_common.http.HttpResultObserver;
import com.kq.platform.kq_common.presenter.BasePresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by Zhou jiaqi on 2019/1/21.
 */
public class LoginPresenter extends BasePresenter {

    private ILoginView iView;
    private LoginModel loginModel;

    public LoginPresenter(ILoginView iView) {
        this.iView = iView;
        this.loginModel = new LoginModelImpl();
    }

    public void login(String userName,String password){
        iView.showWait();
        loginModel.login(userName, password).subscribe(new HttpResultObserver<UserBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onSuccess(HttpResult<UserBean> t) {
                iView.cancelWait();
                iView.loginSuccess(t.getResultVo());
            }

            @Override
            public void _onError(String errorCode, String msg) {
                iView.cancelWait();
                iView.onFailure(errorCode,msg);
            }
        });
    }

    public void autoLogin(String userName, String salt){
        loginModel.login(userName, salt).subscribe(new HttpResultObserver<UserBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onSuccess(HttpResult<UserBean> t) {
                iView.loginSuccess(t.getResultVo());
            }

            @Override
            public void _onError(String errorCode, String msg) {
                iView.onFailure(errorCode,msg);
            }
        });
    }

}
