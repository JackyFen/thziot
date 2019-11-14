package com.kq.platform.kq_common.bean;

/**
 * Created by Zhou jiaqi on 2019/1/18.
 */
public class ServerResultBean {

    /**
     * resultCode : 0
     * internalMsg : 请求成功
     * errorParam :
     * resultMsg : 请求成功
     */

    private String resultCode;
    private String internalMsg;
    private String errorParam;
    private String resultMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getInternalMsg() {
        return internalMsg;
    }

    public void setInternalMsg(String internalMsg) {
        this.internalMsg = internalMsg;
    }

    public String getErrorParam() {
        return errorParam;
    }

    public void setErrorParam(String errorParam) {
        this.errorParam = errorParam;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
