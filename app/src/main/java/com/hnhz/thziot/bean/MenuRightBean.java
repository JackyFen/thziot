package com.hnhz.thziot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhou jiaqi on 2019/8/21.
 */
public class MenuRightBean implements Parcelable {

    private long isSystem;
    private long productId;
    private long roleId;
    private long subitemsId;
    private long userId;
    private List<MenuBean> listProductAccess;
    //private List<?> listRights;


    public long getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(long isSystem) {
        this.isSystem = isSystem;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getSubitemsId() {
        return subitemsId;
    }

    public void setSubitemsId(long subitemsId) {
        this.subitemsId = subitemsId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<MenuBean> getListProductAccess() {
        return listProductAccess;
    }

    public void setListProductAccess(List<MenuBean> listProductAccess) {
        this.listProductAccess = listProductAccess;
    }


    public MenuRightBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.isSystem);
        dest.writeLong(this.productId);
        dest.writeLong(this.roleId);
        dest.writeLong(this.subitemsId);
        dest.writeLong(this.userId);
        dest.writeTypedList(this.listProductAccess);
    }

    protected MenuRightBean(Parcel in) {
        this.isSystem = in.readLong();
        this.productId = in.readLong();
        this.roleId = in.readLong();
        this.subitemsId = in.readLong();
        this.userId = in.readLong();
        this.listProductAccess = in.createTypedArrayList(MenuBean.CREATOR);
    }

    public static final Creator<MenuRightBean> CREATOR = new Creator<MenuRightBean>() {
        @Override
        public MenuRightBean createFromParcel(Parcel source) {
            return new MenuRightBean(source);
        }

        @Override
        public MenuRightBean[] newArray(int size) {
            return new MenuRightBean[size];
        }
    };
}
