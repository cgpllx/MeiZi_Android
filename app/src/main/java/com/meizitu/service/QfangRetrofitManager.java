package com.meizitu.service;


import cc.easyandroid.easycore.EasyExecutor;
import cc.easyandroid.easyhttp.EasyHttpUtils;
import cc.easyandroid.easyhttp.retrofit2.EasyExecutorCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cgpllx on 2016/9/30.
 */
public class QfangRetrofitManager {
    private static ImageApi api;
    public static String DOMAIN="www.ffvvv.cc";
//    public static String DOMAIN="192.168.1.105:8080";

    /**
     * 取得api
     *
     * @return ImageApi
     */
    public static ImageApi getApi() {
        if (api == null) {
            synchronized (QfangRetrofitManager.class) {
                if (api == null) {
                    Retrofit retrofit = new Retrofit.Builder()//
                            .baseUrl("http://" + DOMAIN + "/")//
                            .addConverterFactory(GsonConverterFactory.create(EasyHttpUtils.getInstance().getGson()))//
                            .addCallAdapterFactory(new EasyExecutorCallAdapterFactory())
                            .client(EasyHttpUtils.getInstance().getOkHttpClient())
                            .build();
                    api = retrofit.create(ImageApi.class);
                }
            }
        }
        return api;
    }
}
