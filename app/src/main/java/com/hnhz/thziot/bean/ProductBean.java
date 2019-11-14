package com.hnhz.thziot.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhou jiaqi on 2019/6/6.
 */
public class ProductBean implements Parcelable {

    //产品ID
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

    //界面对象-是否关注
    private boolean isFollowed;

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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public ProductBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.productId);
        dest.writeString(this.description);
        dest.writeString(this.entranceType);
        dest.writeString(this.entranceUrl);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.isUse);
        dest.writeString(this.isUseName);
        dest.writeString(this.productName);
        dest.writeString(this.productNo);
        dest.writeInt(this.usable);
        dest.writeByte(this.isFollowed ? (byte) 1 : (byte) 0);
    }

    protected ProductBean(Parcel in) {
        this.productId = in.readLong();
        this.description = in.readString();
        this.entranceType = in.readString();
        this.entranceUrl = in.readString();
        this.imageUrl = in.readString();
        this.isUse = in.readInt();
        this.isUseName = in.readString();
        this.productName = in.readString();
        this.productNo = in.readString();
        this.usable = in.readInt();
        this.isFollowed = in.readByte() != 0;
    }

    public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
        @Override
        public ProductBean createFromParcel(Parcel source) {
            return new ProductBean(source);
        }

        @Override
        public ProductBean[] newArray(int size) {
            return new ProductBean[size];
        }
    };
}
