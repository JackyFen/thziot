package com.hnhz.thziot.presenter;

import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.bean.SubItemBean;
import com.hnhz.thziot.model.ProductModel;
import com.hnhz.thziot.model.impl.ProductModelImpl;
import com.hnhz.thziot.presenter.view.IProductView;
import com.hnhz.thziot.realm.operator.ProductRealmOp;
import com.kq.platform.kq_common.bean.HttpResult;
import com.kq.platform.kq_common.http.HttpResultObserver;
import com.kq.platform.kq_common.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Zhou jiaqi on 2019/8/16.
 */
public class ProductPresenter extends BasePresenter {

    private IProductView iProductView;
    private ProductModel productModel;

    public ProductPresenter(IProductView iProductView) {
        this.iProductView = iProductView;
        this.productModel = new ProductModelImpl();
    }

    public void getAllProduct(){

//        List<ProductBean> items = new ArrayList<>();
//        for (int i = 0; i < 19; i++) {
//            if(i==11){
//                continue;
//            }
//            ProductBean entity = new ProductBean();
//            entity.setProductName("频道" + i);
//            entity.setProductId(i);
//            if(i==1){
//                entity.setUsable(1);
//            }
//            items.add(entity);
//        }
//
//        ProductRealmOp.saveOrUpdateProductInfo(items);
//        List<ProductBean> followedList = ProductRealmOp.getFollowedProductList();
//        iProductView.getProductSuccess(followedList);

        iProductView.showWait();
        productModel.getAllProductInfo()
                .doOnNext(new Consumer<HttpResult<List<ProductBean>>>() {
                    @Override
                    public void accept(HttpResult<List<ProductBean>> productBeanHttpResult) throws Exception {
                        List<ProductBean> allProduct = productBeanHttpResult.getResultVo();
                        if(allProduct!=null) {
                            ProductRealmOp.saveOrUpdateProductInfo(allProduct);
                        }
                    }
                })
                .subscribe(new HttpResultObserver<List<ProductBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(HttpResult<List<ProductBean>> t) {
                        List<ProductBean> followedList = ProductRealmOp.getFollowedProductList();
                        iProductView.cancelWait();
                        iProductView.getProductSuccess(followedList);
                    }

                    @Override
                    public void _onError(String errorCode, String msg) {
                        iProductView.cancelWait();
                        iProductView.onFailure(errorCode,msg);
                    }
                });



        //TODO:获取数据：存储所有产品数据，1.如果是第1次，设定7个关注字段为true。2.之后获取的数据跟数据库的进行比对再update相关字段。3.最终传设定好的List数据

    }


    public void getSubItem(ProductBean productBean){

        iProductView.showWait();
        productModel.getAllSubItemInfo(productBean.getProductId())
                .subscribe(new HttpResultObserver<List<SubItemBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(HttpResult<List<SubItemBean>> t) {
                        iProductView.cancelWait();
                        iProductView.getSubItemSuccess(t.getResultVo(),productBean);
                    }

                    @Override
                    public void _onError(String errorCode, String msg) {
                        iProductView.cancelWait();
                        iProductView.onFailure(errorCode,msg);
                    }
                });


    }
}
