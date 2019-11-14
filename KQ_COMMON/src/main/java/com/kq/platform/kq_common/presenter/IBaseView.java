package com.kq.platform.kq_common.presenter;

/**
 * Created by Zhou Jiaqi on 2017/10/18 : 10:00.
 */

public interface IBaseView {
    /**
     * 等待框
     * @param mes
     */
    void showWait(String mes);

    /**
     * 等待框
     */
    void showWait();

    void cancelWait();

    /**
     *  吐丝显示
     * @param mes
     */
    void showMessage(String mes);

    void onFailure(String flag,String msg);

    void showLoading();

    void hideLoading();
}
