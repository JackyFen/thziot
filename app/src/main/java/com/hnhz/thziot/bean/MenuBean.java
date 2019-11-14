package com.hnhz.thziot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Zhou jiaqi on 2019/8/21.
 */
public class MenuBean implements Parcelable {

    //显示组件
    private String component;
    //菜单动作地址
    private String href;
    //菜单图标
    private String icon;
    //是否显示 0-不显示 1-显示
    private int isShow;
    //是否系统菜单
    private int isSystem;
    //菜单名称
    private String name;
    //菜单ID
    private int nodeId;
    //权限code
    private String permission;
    //上级菜单ID
    private int pid;
    //产品id
    private int productId;
    //路由
    private String route;
    //菜单显示顺序
    private int sort;
    //菜单类型 T_node_type
    private int type;

    //子菜单集合
    private List<MenuBean> children;

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(int isSystem) {
        this.isSystem = isSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<MenuBean> getChildren() {
        return children;
    }

    public void setChildren(List<MenuBean> children) {
        this.children = children;
    }

    public MenuBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.component);
        dest.writeString(this.href);
        dest.writeString(this.icon);
        dest.writeInt(this.isShow);
        dest.writeInt(this.isSystem);
        dest.writeString(this.name);
        dest.writeInt(this.nodeId);
        dest.writeString(this.permission);
        dest.writeInt(this.pid);
        dest.writeInt(this.productId);
        dest.writeString(this.route);
        dest.writeInt(this.sort);
        dest.writeInt(this.type);
        dest.writeTypedList(this.children);
    }

    protected MenuBean(Parcel in) {
        this.component = in.readString();
        this.href = in.readString();
        this.icon = in.readString();
        this.isShow = in.readInt();
        this.isSystem = in.readInt();
        this.name = in.readString();
        this.nodeId = in.readInt();
        this.permission = in.readString();
        this.pid = in.readInt();
        this.productId = in.readInt();
        this.route = in.readString();
        this.sort = in.readInt();
        this.type = in.readInt();
        this.children = in.createTypedArrayList(MenuBean.CREATOR);
    }

    public static final Creator<MenuBean> CREATOR = new Creator<MenuBean>() {
        @Override
        public MenuBean createFromParcel(Parcel source) {
            return new MenuBean(source);
        }

        @Override
        public MenuBean[] newArray(int size) {
            return new MenuBean[size];
        }
    };
}
