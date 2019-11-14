package com.hnhz.thziot.realm.operator;

import android.util.Log;

import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.realm.RealmConverter;
import com.hnhz.thziot.realm.RealmTool;
import com.hnhz.thziot.realm.realmobj.ProductRealmObj;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Zhou jiaqi on 2019/8/16.
 */
public class ProductRealmOp extends BaseRealmOp{

    public static List<ProductRealmObj> getAllProductRealmList(){
        return RealmTool.tryExecuteFetcherList(realm -> realm.copyFromRealm(realm.where(ProductRealmObj.class).findAll()));
    }

    public static List<ProductBean> getAllProductList(){
        return RealmTool.tryExecuteFetcherList(realm -> {
            RealmResults<ProductRealmObj> rr = realm.where(ProductRealmObj.class).findAll();
            List<ProductRealmObj> list = realm.copyFromRealm(rr);
            return RealmConverter.ListRealmToListNormal(list,ProductBean.class);
        });
    }

    public static void saveOrUpdateProductInfo(List<ProductBean> productList){
        List<ProductRealmObj> allProductRealmObjList = getAllProductRealmList();

        if(allProductRealmObjList.size() == 0) {
            RealmTool.tryExecuteTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int i = 0;
                    List<String> noNeedFieldList = new ArrayList<>();
                    noNeedFieldList.add("isFollowed");
                    noNeedFieldList.add("adapterPosition");
                    List<ProductRealmObj> newProductRealmList = RealmConverter.listToListRealm(productList,ProductRealmObj.class,noNeedFieldList);

                    for (ProductRealmObj productRealmObj : newProductRealmList) {
                        if (productRealmObj.getUsable() == 1) {
                            productRealmObj.setFollowed(true);
                            productRealmObj.setAdapterPosition(i);
                            i++;
                        } else {
                            productRealmObj.setFollowed(false);
                        }
                    }
                    realm.insert(newProductRealmList);
                    if (i < 7) {
                        RealmResults<ProductRealmObj> noPositionRR = realm.where(ProductRealmObj.class).isNull("adapterPosition").findAll();
                        for (ProductRealmObj productRealmObj : noPositionRR) {
                            productRealmObj.setFollowed(true);
                            productRealmObj.setAdapterPosition(i);
                            i++;
                            if (i == 7)
                                break;
                        }
                    }
                }
            });
        }else{
            RealmTool.tryExecuteTransaction(realm ->{

                Long[] usedProductId = new Long[productList.size()];
                for(int i=0;i<productList.size();i++){
                    ProductBean productBean = productList.get(i);
                   ProductRealmObj productRealmObj = realm.where(ProductRealmObj.class)
                            .equalTo("productId",productBean.getProductId())
                            .findFirst();
                    usedProductId[i] = productBean.getProductId();
                    if(productRealmObj!=null){
                       productRealmObj.setDescription(productBean.getDescription());
                       productRealmObj.setEntranceType(productBean.getEntranceType());
                       productRealmObj.setEntranceUrl(productBean.getEntranceUrl());
                       productRealmObj.setImageUrl(productBean.getImageUrl());
                       productRealmObj.setIsUse(productBean.getIsUse());
                       productRealmObj.setIsUseName(productBean.getIsUseName());
                       productRealmObj.setProductName(productBean.getProductName());
                       productRealmObj.setProductNo(productBean.getProductNo());
                       productRealmObj.setUsable(productBean.getUsable());
                   }else{
                       realm.insert((ProductRealmObj)RealmConverter.normalToRealmObj(productBean,ProductRealmObj.class));
                   }
                }

                realm.where(ProductRealmObj.class)
                        .not()
                        .in("productId",usedProductId).findAll().deleteAllFromRealm();
            });
        }

    }

    public static List<ProductBean> getFollowedProductList(){
         List<ProductRealmObj> follwedProductRealmList = RealmTool.tryExecuteFetcherList(realm -> {
                RealmResults<ProductRealmObj> rr =
                        realm.where(ProductRealmObj.class)
                                .equalTo("isFollowed",true)
                                .sort("adapterPosition",Sort.ASCENDING)
                                .findAll();
                return realm.copyFromRealm(rr);
            });
        return RealmConverter.ListRealmToListNormal(follwedProductRealmList,ProductBean.class);
    }

    public static void updateFollowedProductListPosition(List<ProductBean> productBeanList){
        RealmTool.tryExecuteTransaction(realm -> {
            for(int i= 0 ; i<productBeanList.size();i++){
                long productId = productBeanList.get(i).getProductId();

                ProductRealmObj productRealmObj = realm.where(ProductRealmObj.class).equalTo("productId",productId).findFirst();
                if(productRealmObj != null) {
                    productRealmObj.setAdapterPosition(i);
                }
            }
        });
    }

    public static void updateFollowedProductList(List<ProductBean> productList){

        RealmTool.tryExecuteTransaction(realm -> {
            for(ProductBean productBean : productList){
                ProductRealmObj productRealmObj = realm.where(ProductRealmObj.class)
                        .equalTo("productId",productBean.getProductId())
                        .findFirst();
                if(productRealmObj!=null) {
                    productRealmObj.setFollowed(productBean.isFollowed());
                    if(!productRealmObj.isFollowed()){
                        productRealmObj.setAdapterPosition(null);
                    }else if(productRealmObj.isFollowed() && productRealmObj.getAdapterPosition()==null){
                        productRealmObj.setAdapterPosition(Integer.MAX_VALUE);
                    }
                }
            }

            RealmResults<ProductRealmObj> rr =
                    realm.where(ProductRealmObj.class)
                            .equalTo("isFollowed",true)
                            .sort("adapterPosition",Sort.ASCENDING)
                            .findAll();

            List<ProductRealmObj> productRealmObjList = realm.copyFromRealm(rr);

            for(int i = 0; i<productRealmObjList.size();i++){
                ProductRealmObj productRealmObj = productRealmObjList.get(i);
                productRealmObj.setAdapterPosition(i);
                realm.copyToRealmOrUpdate(productRealmObj);
            }
        });


        //saveCollectionsOrUpdate(newProductRealmList);
    }

    public static void unFollowProduct(ProductBean productBean){
        RealmTool.tryExecuteTransaction(realm -> {
            ProductRealmObj productRealmObj = realm.where(ProductRealmObj.class).equalTo("productId",productBean.getProductId()).findFirst();
            if(productRealmObj!=null)
                productRealmObj.setFollowed(false);
        });
    }



}
