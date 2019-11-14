package com.hnhz.thziot.bean.login;

/**
 * Created by Zhou jiaqi on 2018/7/10.
 */

public class OAuthError {

    private String error_description;
    private String error;

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
