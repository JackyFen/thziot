package com.hnhz.thziot.bean.login;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Zhou jiaqi on 2018/7/31.
 */

public class UserBean implements Parcelable {


    private Long lastLoginTime;
    private String certificateStatus;
    private String jsessionid;
    private String salt;
    private String userMobile;
    private String clientinfo;
    private String userAccount;
    private String headUrl;
    private String userType;
    private String userName;
    private List<OrgBean> orgList;

    public UserBean() {
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(String certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getClientinfo() {
        return clientinfo;
    }

    public void setClientinfo(String clientinfo) {
        this.clientinfo = clientinfo;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public List<OrgBean> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<OrgBean> orgList) {
        this.orgList = orgList;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.lastLoginTime);
        dest.writeString(this.certificateStatus);
        dest.writeString(this.jsessionid);
        dest.writeString(this.salt);
        dest.writeString(this.userMobile);
        dest.writeString(this.clientinfo);
        dest.writeString(this.userAccount);
        dest.writeString(this.headUrl);
        dest.writeString(this.userType);
        dest.writeString(this.userName);
        dest.writeTypedList(this.orgList);
    }

    protected UserBean(Parcel in) {
        this.lastLoginTime = (Long) in.readValue(Long.class.getClassLoader());
        this.certificateStatus = in.readString();
        this.jsessionid = in.readString();
        this.salt = in.readString();
        this.userMobile = in.readString();
        this.clientinfo = in.readString();
        this.userAccount = in.readString();
        this.headUrl = in.readString();
        this.userType = in.readString();
        this.userName = in.readString();
        this.orgList = in.createTypedArrayList(OrgBean.CREATOR);
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
