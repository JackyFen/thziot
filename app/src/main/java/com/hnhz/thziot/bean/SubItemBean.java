package com.hnhz.thziot.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhou jiaqi on 2019/6/4.
 */
public class SubItemBean extends ListPopDownBean implements Parcelable {

    //所属服务ID
    private long serviceId;
    //子项目ID
    private long subitemsId;
    //业务类型
    private String businessType;
    //业务类型名称
    private String businessTypeName;
    //创建时间
    private long createTime;
    //付费方式 T_deduct_mode
    private String deductMode;
    //付费方式名称
    private String deductModeName;
    //是否自动续费 0 -否 1 -是
    private int isRenew;
    //开通结束时间
    private long openEndTime;
    //开通起始时间
    private long openStartTime;
    //开通状态S_open_staus
    private String openStaus;
    //状态名称
    private String openStausName;
    //组织号
    private long orgId;
    //服务对象
    private String orgName;
    //原价
    private int originalPrice;
    //应付金额
    private int payPrice;
    //产品类型T_share_product_type
    private String productType;
    //产品类型名称
    private String productTypeName;
    //服务名称
    private String serviceName;
    //子项目服务对象T_service_object
    private String serviceObject;
    //服务对象名称
    private String serviceObjectName;
    //服务类型
    private String serviceType;
    //服务类型名称
    private String serviceTypeName;
    //子项目名称
    private String subitemsName;
    //服务简述
    private String summaryDesc;
    //更新时间
    private int updateTime;
    //账户ID
    private long userId;

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public long getSubitemsId() {
        return subitemsId;
    }

    public void setSubitemsId(long subitemsId) {
        this.subitemsId = subitemsId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDeductMode() {
        return deductMode;
    }

    public void setDeductMode(String deductMode) {
        this.deductMode = deductMode;
    }

    public String getDeductModeName() {
        return deductModeName;
    }

    public void setDeductModeName(String deductModeName) {
        this.deductModeName = deductModeName;
    }

    public int getIsRenew() {
        return isRenew;
    }

    public void setIsRenew(int isRenew) {
        this.isRenew = isRenew;
    }

    public long getOpenEndTime() {
        return openEndTime;
    }

    public void setOpenEndTime(long openEndTime) {
        this.openEndTime = openEndTime;
    }

    public long getOpenStartTime() {
        return openStartTime;
    }

    public void setOpenStartTime(long openStartTime) {
        this.openStartTime = openStartTime;
    }

    public String getOpenStaus() {
        return openStaus;
    }

    public void setOpenStaus(String openStaus) {
        this.openStaus = openStaus;
    }

    public String getOpenStausName() {
        return openStausName;
    }

    public void setOpenStausName(String openStausName) {
        this.openStausName = openStausName;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(int payPrice) {
        this.payPrice = payPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(String serviceObject) {
        this.serviceObject = serviceObject;
    }

    public String getServiceObjectName() {
        return serviceObjectName;
    }

    public void setServiceObjectName(String serviceObjectName) {
        this.serviceObjectName = serviceObjectName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getSubitemsName() {
        return subitemsName;
    }

    public void setSubitemsName(String subitemsName) {
        this.subitemsName = subitemsName;
    }

    public String getSummaryDesc() {
        return summaryDesc;
    }

    public void setSummaryDesc(String summaryDesc) {
        this.summaryDesc = summaryDesc;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public SubItemBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.serviceId);
        dest.writeLong(this.subitemsId);
        dest.writeString(this.businessType);
        dest.writeString(this.businessTypeName);
        dest.writeLong(this.createTime);
        dest.writeString(this.deductMode);
        dest.writeString(this.deductModeName);
        dest.writeInt(this.isRenew);
        dest.writeLong(this.openEndTime);
        dest.writeLong(this.openStartTime);
        dest.writeString(this.openStaus);
        dest.writeString(this.openStausName);
        dest.writeLong(this.orgId);
        dest.writeString(this.orgName);
        dest.writeInt(this.originalPrice);
        dest.writeInt(this.payPrice);
        dest.writeString(this.productType);
        dest.writeString(this.productTypeName);
        dest.writeString(this.serviceName);
        dest.writeString(this.serviceObject);
        dest.writeString(this.serviceObjectName);
        dest.writeString(this.serviceType);
        dest.writeString(this.serviceTypeName);
        dest.writeString(this.subitemsName);
        dest.writeString(this.summaryDesc);
        dest.writeInt(this.updateTime);
        dest.writeValue(this.userId);
    }

    protected SubItemBean(Parcel in) {
        this.serviceId = in.readLong();
        this.subitemsId = in.readLong();
        this.businessType = in.readString();
        this.businessTypeName = in.readString();
        this.createTime = in.readLong();
        this.deductMode = in.readString();
        this.deductModeName = in.readString();
        this.isRenew = in.readInt();
        this.openEndTime = in.readLong();
        this.openStartTime = in.readLong();
        this.openStaus = in.readString();
        this.openStausName = in.readString();
        this.orgId = in.readLong();
        this.orgName = in.readString();
        this.originalPrice = in.readInt();
        this.payPrice = in.readInt();
        this.productType = in.readString();
        this.productTypeName = in.readString();
        this.serviceName = in.readString();
        this.serviceObject = in.readString();
        this.serviceObjectName = in.readString();
        this.serviceType = in.readString();
        this.serviceTypeName = in.readString();
        this.subitemsName = in.readString();
        this.summaryDesc = in.readString();
        this.updateTime = in.readInt();
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<SubItemBean> CREATOR = new Creator<SubItemBean>() {
        @Override
        public SubItemBean createFromParcel(Parcel source) {
            return new SubItemBean(source);
        }

        @Override
        public SubItemBean[] newArray(int size) {
            return new SubItemBean[size];
        }
    };
}
