package com.meizitu.service.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * add header
 */
public class PublicHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("encryption", "1")// 加密
                .addHeader("Cache-Control", "public")//
                .addHeader("Cache-Duration", "360000")//native cache 100 hour
                .build();
        return chain.proceed(request);
    }

}