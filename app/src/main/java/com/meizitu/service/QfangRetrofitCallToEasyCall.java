package com.meizitu.service;

import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;


public class QfangRetrofitCallToEasyCall<T> extends RetrofitCallToEasyCall<T> {

    public QfangRetrofitCallToEasyCall(retrofit2.Call call) {
        super(call);
    }

}