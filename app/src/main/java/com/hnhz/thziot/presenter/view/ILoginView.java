package com.hnhz.thziot.presenter.view;

import com.hnhz.thziot.bean.login.UserBean;
import com.kq.platform.kq_common.presenter.IBaseView;

/**
 * Created by Zhou jiaqi on 2019/1/21.
 */
public interface ILoginView extends IBaseView {
    void loginSuccess(UserBean userBean);
}
