package com.kq.platform.kq_common.presenter;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Zhou jiaqi on 2018/3/9.
 */

public abstract class BasePresenter{

    protected CompositeDisposable compositeDisposable;

    public BasePresenter() {
    }

    /**
     * 管理所有建立的链接
     */
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 在onDestroy中清空 mCompositeDisposable
     */
    public void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
