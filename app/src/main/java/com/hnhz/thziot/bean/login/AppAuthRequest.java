package com.hnhz.thziot.bean.login;

/**
 * Created by Zhou jiaqi on 2019/1/18.
 */

public class AppAuthRequest {

    private String client_id;
    private String response_type;
    private String userName;
    private String password;

    public AppAuthRequest(String client_id, String response_type, String userName, String password) {
        this.client_id = client_id;
        this.response_type = response_type;
        this.userName = userName;
        this.password = password;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
