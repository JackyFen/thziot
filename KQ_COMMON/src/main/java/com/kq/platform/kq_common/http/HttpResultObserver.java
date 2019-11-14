package com.kq.platform.kq_common.http;


import com.kq.platform.kq_common.bean.HttpResult;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;

/**
 * Created by Zhou jiaqi on 2018/3/9.
 */

public abstract class HttpResultObserver<T> implements Observer<HttpResult<T>>{

    @Override
    public void onNext(HttpResult<T> t) {
        if (t.getServerResult()!=null && t.getServerResult().getResultCode().equals("0")) {
            onSuccess(t);
        } else {
            _onError(t.getServerResult()==null?"999":t.getServerResult().getResultMsg(),t.getServerResult()==null?"网络连接错误":t.getServerResult().getResultMsg());
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //在这里做全局的错误处理
        if (e instanceof ConnectException ||
                e instanceof SocketTimeoutException ||
                e instanceof TimeoutException) {
            //网络错误
            _onError("9999","网络连接超时");
        } else if(e instanceof CustomHttpException) {
            _onError(((CustomHttpException) e).getErrorCode(),e.getMessage());
        } else{
            _onError("999", "网络连接错误");
        }
    }

    public abstract void onSuccess(HttpResult<T> t);

    public abstract void _onError(String errorCode,String msg);
}
