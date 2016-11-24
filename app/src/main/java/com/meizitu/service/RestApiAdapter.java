package com.meizitu.service;


import com.google.gson.Gson;

import cc.easyandroid.easycache.EasyHttpCache;
import cc.easyandroid.easyhttp.retrofit2.EasyExecutorCallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit的实体类
 */
public class RestApiAdapter {
    public static Retrofit getInstance(Gson gson, EasyHttpCache cache, OkHttpClient client) {
        return new Retrofit.Builder()//
                .baseUrl("http://" + ImageApi.DOMAIN + "/")//
                .addConverterFactory(GsonConverterFactory.create(gson))//
                .addCallAdapterFactory(new EasyExecutorCallAdapterFactory(cache))
                .client(client)
                .build();
    }
}
