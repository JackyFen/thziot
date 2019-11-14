package com.kq.platform.kq_common.http;

/**
 * Created by Zhou jiaqi on 2019/1/18.
 */
public class CustomHttpException extends Exception {

    private static final long serialVersionUID = -5009483694417870930L;

    private String errorCode;

    public CustomHttpException(String errorCode) {
        this.errorCode = errorCode;
    }

    public CustomHttpException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomHttpException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CustomHttpException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
