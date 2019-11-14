package com.kq.platform.kq_common.helper;

import com.kq.platform.kq_common.bean.HttpResult;

/**
 * Created by Zhou jiaqi on 2018/7/11.
 */

public class RxHelper {

//    public static <T> ObservableTransformer<HttpResult<T>, HttpResult<T>> refreshToken()   {
//        return observable -> observable.flatMap(tHttpResult -> {
//            if (tHttpResult.getFlag() == 101) {
//                return Observable.error(new Throwable(BaseErrorConstant.THROWABLE_MSG_TOKEN_VALID));
//            }
//            return Observable.just(tHttpResult);
//        }).retryWhen(observableThrowable -> {
//            return observableThrowable.flatMap(throwable -> {
//                if (throwable.getMessage().equals(BaseErrorConstant.THROWABLE_MSG_TOKEN_VALID)) {
//
//                    PrefsUtils prefsUtils = new PrefsUtils(GlobalApplication.getContext(), BaseConstant.TEST_LOGIN);
//                    PrefsUtils prefsUtils1 = new PrefsUtils(GlobalApplication.getContext(), BaseConstant.PREF_TOKEN_FILE);
//                    return Observable.just(1);
//                }
//                return Observable.just(1);
//            });
//        });
//    }

//    public static Observable obj2MapRequest(List<Object> object,Observable request){
//        Map<String, Object> map;
//        Observable.just(map)
//                .map(Map::entrySet)
//                .flatMapIterable(entries -> entries)
//                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue()))
//                .toMap(e -> e)
//                .subscribe(m -> {
//                    //do something with map
//                });
//    }
}
