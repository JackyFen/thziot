package com.hnhz.thziot.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.hnhz.thziot.R;
import com.hnhz.thziot.bean.login.LoginData;
import com.hnhz.thziot.bean.login.UserBean;
import com.hnhz.thziot.presenter.LoginPresenter;
import com.hnhz.thziot.presenter.view.ILoginView;
import com.kq.platform.kq_common.global.BaseConstant;
import com.hnhz.thziot.global.GlobalApplication;
import com.kq.platform.kq_common.ui.BaseActivity;
import com.kq.platform.kq_common.utils.PrefsUtils;
import com.kq.platform.kq_common.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by Zhou jiaqi on 2019/1/18.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, ILoginView {

    @BindView(R.id.et_login_account)
    EditText etLoginAccount;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initWidget() {
        presenter = new LoginPresenter(this);
        initBarVisible(false);
        StatusBarUtil.setStatusBarColor(this,R.color.white, true);
    }

    @Override
    protected void bindLogic() {
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                String userName = etLoginAccount.getText().toString();
                String password = etLoginPassword.getText().toString();
                if(TextUtils.isEmpty(userName)){
                    showToast("用户名不能为空");
                    break;
                }
                if(TextUtils.isEmpty(password)){
                    showToast("密码不能为空");
                    break;
                }
                presenter.login(userName, password);
                //presenter.getMyManageSubItem();
                break;
        }
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        LoginData.setGlobalUserBean(userBean);
        PrefsUtils prefsUtils = new PrefsUtils(GlobalApplication.getContext(), BaseConstant.PREF_TOKEN_FILE);
        prefsUtils.put(BaseConstant.PREF_KEY_JSESSIONID, userBean.getJsessionid());
        prefsUtils.put("salt", userBean.getSalt());
        prefsUtils.put("userName", userBean.getUserAccount());
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String flag, String msg) {
        super.onFailure(flag, msg);
        showToast(msg);
    }

}
