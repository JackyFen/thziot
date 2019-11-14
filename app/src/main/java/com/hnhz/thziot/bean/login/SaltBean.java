package com.hnhz.thziot.bean.login;

/**
 * Created by Zhou jiaqi on 2019/1/18.
 */
public class SaltBean {
    private String salt;
    private String time;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
