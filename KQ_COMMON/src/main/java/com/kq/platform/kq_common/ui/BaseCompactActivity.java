package com.kq.platform.kq_common.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kq.platform.kq_common.presenter.BasePresenter;
import com.kq.platform.kq_common.presenter.IBaseView;
import com.kq.platform.kq_common.ui.widget.base.BaseToast;
import com.kq.platform.kq_common.ui.widget.base.LoadDialog;

import java.lang.ref.WeakReference;

import kq.platform.kq_common.R;

/**
 * Created by Zhou jiaqi on 2018/7/4.
 */

public abstract class BaseCompactActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {

    protected LoadDialog loadDialog;

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showCustomToastS(String mes) {
        Message message = Message.obtain();
        message.obj = mes;
        message.what = showCustomToastS;
        baseHandler.sendMessage(message);
    }

    public void showCustomToastL(String mes) {
        Message message = Message.obtain();
        message.obj = mes;
        message.what = showCustomToastL;
        baseHandler.sendMessage(message);
    }

    public void showToast(String mes) {
        Message message = Message.obtain();
        message.obj = mes;
        message.what = showToast;
        baseHandler.sendMessage(message);
    }

    @Override
    public void showWait(final String mes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadDialog == null)
                    loadDialog = new LoadDialog(BaseCompactActivity.this);
                loadDialog.setTextTip(String.valueOf(mes));
                loadDialog.show();
            }
        });
    }


    @Override
    public void showWait() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadDialog == null)
                    loadDialog = new LoadDialog(BaseCompactActivity.this);
                loadDialog.setTextTip(getString(R.string.common_loading));
                loadDialog.show();
            }
        });

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void showWait(final String message, final boolean isCancel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadDialog == null)
                    loadDialog = new LoadDialog(BaseCompactActivity.this);
                loadDialog.setTextTip(message);
                loadDialog.setCancelable(isCancel);
                loadDialog.show();

            }
        });
    }


    @Override
    public void cancelWait() {
        Message message = Message.obtain();
        message.what = cancelWaitMes;
        baseHandler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        if(presenter!=null){
            presenter.clearDisposable();
        }
        super.onDestroy();
    }

    protected static final int showCustomToastS = 2017;
    protected static final int showCustomToastL = 2018;
    protected static final int showToast = 2019;

    protected static final int showWait = 2022;
    protected static final int showWaitMes = 2023;
    protected static final int cancelWaitMes = 2024;

    protected BaseHandler baseHandler = new BaseHandler(this);

    private static class BaseHandler extends Handler {
        public WeakReference<BaseCompactActivity> reference;

        public BaseHandler(BaseCompactActivity activity) {
            reference = new WeakReference<BaseCompactActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseCompactActivity instance = reference.get();
            if (instance != null) {
                switch (msg.what) {
                    case showCustomToastS:
                        BaseToast.makeToast(instance.getApplicationContext(), String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
                        break;
                    case showCustomToastL:
                        BaseToast.makeToast(instance.getApplicationContext(), String.valueOf(msg.obj), Toast.LENGTH_LONG).show();
                        break;
                    case showToast:
                        Toast.makeText(instance.getApplicationContext(), String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
                        break;
                    case showWaitMes:

                        break;
                    case showWait:

                        break;
                    case cancelWaitMes:
                        if (instance.loadDialog != null && instance.loadDialog.isShowing())
                            instance.loadDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void showMessage(final String mes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseCompactActivity.this.getApplicationContext(), mes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFailure(String flag, String msg) {
    }
}
