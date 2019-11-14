package com.hnhz.thziot.realm.realmobj;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Zhou jiaqi on 2019/8/21.
 */
public class ProductRealmObj extends RealmObject {

    //产品ID
    @PrimaryKey
    private long productId;
    //应用描述
    private String description;
    //入口类型
    private String entranceType;
    //入口地址
    private String entranceUrl;
    //图标地址
    private String imageUrl;
    //是否启用 0-不可用 1-可用 2-删除
    private int isUse;
    //是否启用名称
    private String isUseName;
    //产品名称
    private String productName;
    //产品编号
    private String productNo;
    //用户是否有该产品实现权限 0-不可用 1-可用
    private int usable;

    //Realm对象--是否关注
    private boolean isFollowed;
    //Realm对象--List排序;
    private Integer adapterPosition;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntranceType() {
        return entranceType;
    }

    public void setEntranceType(String entranceType) {
        this.entranceType = entranceType;
    }

    public String getEntranceUrl() {
        return entranceUrl;
    }

    public void setEntranceUrl(String entranceUrl) {
        this.entranceUrl = entranceUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getIsUseName() {
        return isUseName;
    }

    public void setIsUseName(String isUseName) {
        this.isUseName = isUseName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getUsable() {
        return usable;
    }

    public void setUsable(int usable) {
        this.usable = usable;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public Integer getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(Integer adapterPosition) {
        this.adapterPosition = adapterPosition;
    }
}
