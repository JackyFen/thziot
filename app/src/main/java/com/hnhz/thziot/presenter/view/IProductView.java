package com.hnhz.thziot.presenter.view;

import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.bean.SubItemBean;
import com.kq.platform.kq_common.presenter.IBaseView;

import java.util.List;

/**
 * Created by Zhou jiaqi on 2019/8/16.
 */
public interface IProductView extends IBaseView {

    void getProductSuccess(List<ProductBean> followedProductList);

    void getSubItemSuccess(List<SubItemBean> subItemList,ProductBean productBean);

}
