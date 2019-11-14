package com.hnhz.thziot.presenter;

import com.hnhz.thziot.bean.MenuBean;
import com.hnhz.thziot.bean.MenuRightBean;
import com.hnhz.thziot.model.ProductModel;
import com.hnhz.thziot.model.impl.ProductModelImpl;
import com.hnhz.thziot.presenter.view.IProductContentView;
import com.kq.platform.kq_common.bean.HttpResult;
import com.kq.platform.kq_common.http.HttpResultObserver;
import com.kq.platform.kq_common.presenter.BasePresenter;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Zhou jiaqi on 2019/8/29.
 */
public class ProductContentPresenter extends BasePresenter {

    private IProductContentView iProductContentView;
    private ProductModel productModel;

    public ProductContentPresenter(IProductContentView iProductContentView) {
        this.iProductContentView = iProductContentView;
        productModel = new ProductModelImpl();
    }

    public void getMenuList(Long subItemId,Long productId){
        iProductContentView.showWait();
        productModel.getMenuInfo(subItemId,productId)
                .subscribe(new HttpResultObserver<MenuRightBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(HttpResult<MenuRightBean> t) {
                        iProductContentView.cancelWait();
                        iProductContentView.getMenu(t.getResultVo());
                    }

                    @Override
                    public void _onError(String errorCode, String msg) {
                        iProductContentView.cancelWait();
                        iProductContentView.onFailure(errorCode,msg);
                    }
                });
    }
}
