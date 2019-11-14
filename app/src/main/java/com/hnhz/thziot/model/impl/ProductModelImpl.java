package com.hnhz.thziot.model.impl;

import com.hnhz.thziot.bean.MenuBean;
import com.hnhz.thziot.bean.MenuRightBean;
import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.bean.SubItemBean;
import com.hnhz.thziot.http.ApiManager;
import com.hnhz.thziot.model.ProductModel;
import com.kq.platform.kq_common.bean.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhou jiaqi on 2019/8/28.
 */
public class ProductModelImpl implements ProductModel {

    @Override
    public Observable<HttpResult<List<ProductBean>>> getAllProductInfo() {
        return ApiManager.getAllProduct().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpResult<List<SubItemBean>>> getAllSubItemInfo(Long productId) {
        return ApiManager.getAllSubItems(productId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpResult<MenuRightBean>> getMenuInfo(Long subItemsId, Long productId) {
        return ApiManager.getMenuInfo(subItemsId,productId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
