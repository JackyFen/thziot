package com.hnhz.thziot.bean.login;

import android.os.Parcel;
import android.os.Parcelable;

public class OrgBean implements Parcelable {
    private Long orgId;
    private Long userId;
    private String orgName;
    private String certificateStatus;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(String certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.orgId);
        dest.writeValue(this.userId);
        dest.writeString(this.orgName);
        dest.writeString(this.certificateStatus);
    }

    public OrgBean() {
    }

    protected OrgBean(Parcel in) {
        this.orgId = (Long) in.readValue(Long.class.getClassLoader());
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
        this.orgName = in.readString();
        this.certificateStatus = in.readString();
    }

    public static final Creator<OrgBean> CREATOR = new Creator<OrgBean>() {
        @Override
        public OrgBean createFromParcel(Parcel source) {
            return new OrgBean(source);
        }

        @Override
        public OrgBean[] newArray(int size) {
            return new OrgBean[size];
        }
    };
}
