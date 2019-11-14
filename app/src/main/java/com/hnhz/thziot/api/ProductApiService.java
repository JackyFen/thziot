package com.hnhz.thziot.api;


import com.hnhz.thziot.bean.MenuBean;
import com.hnhz.thziot.bean.MenuRightBean;
import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.bean.SubItemBean;
import com.kq.platform.kq_common.bean.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Zhou jiaqi on 2019/8/28.
 */
public interface ProductApiService {

    @GET("product/getAppProduct")
    Observable<HttpResult<List<ProductBean>>> getAllProductInfo();

    @GET("userSubitems/getSubitems/{productId}")
    Observable<HttpResult<List<SubItemBean>>> getAllSubItems(@Path("productId") Long productId);

    @GET("userSubitems/userAuth/{subitemsId}/{productId}")
    Observable<HttpResult<MenuRightBean>> getMenuInfo(@Path("subitemsId") Long  subitemsId, @Path("productId") Long productId);
}
