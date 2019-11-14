package com.hnhz.thziot.model;

import com.hnhz.thziot.bean.MenuRightBean;
import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.bean.SubItemBean;
import com.kq.platform.kq_common.bean.HttpResult;
import com.kq.platform.kq_common.model.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhou jiaqi on 2019/8/28.
 */
public interface ProductModel extends IBaseModel {

    Observable<HttpResult<List<ProductBean>>> getAllProductInfo();

    Observable<HttpResult<List<SubItemBean>>> getAllSubItemInfo(Long productId);

    Observable<HttpResult<MenuRightBean>> getMenuInfo(Long subItemsId, Long productId);
}
