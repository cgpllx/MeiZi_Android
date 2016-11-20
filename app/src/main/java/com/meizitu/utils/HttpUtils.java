package com.meizitu.utils;

import android.os.Bundle;

import java.io.File;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.EasyHttpUtils;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.core.OKHttp3RequestFactory;
import cc.easyandroid.easymvp.presenter.EasyWorkPresenter;
import cc.easyandroid.easyutils.EasyUtils;

/**
 * http工具類，根據需要自己添加
 */
public class HttpUtils {

    public static <T> EasyCall<T> creatPostCall(String url, Bundle bundle, EasyWorkPresenter<T> presenter, Map<String, String> header) {
        okhttp3.Request request = OKHttp3RequestFactory.createPostRequest(url, header, EasyUtils.BundleToMap(bundle));
        return EasyHttpUtils.getInstance().executeHttpRequestToCall(request, presenter.getDeliverResultType());
    }

    /**
     * post请求
     *
     * @param url
     * @param paras     参数
     * @param presenter
     * @param <T>
     * @return
     */
    public static <T> EasyCall<T> creatPostCall(String url, Map<String, String> paras, EasyWorkPresenter<T> presenter) {
        okhttp3.Request request = OKHttp3RequestFactory.createPostRequest(url, paras);
        return EasyHttpUtils.getInstance().executeHttpRequestToCall(request, presenter.getDeliverResultType());
    }

    /**
     * get请求
     *
     * @param url
     * @param presenter
     * @param <T>
     * @return
     */
    public static <T> EasyCall<T> creatGetCallNoCache(String url, EasyWorkPresenter<T> presenter) {
        okhttp3.Request request = OKHttp3RequestFactory.createGetRequest(url);
        return EasyHttpUtils.getInstance().executeHttpRequestToCall(request, presenter.getDeliverResultType());
    }

    /**
     * 先网络后缓存
     *
     * @param url
     * @param presenter
     * @param <T>
     * @return
     */
    public static <T> EasyCall<T> creatGetCall(String url, EasyWorkPresenter<T> presenter) {
        HashMap<String, String> header = new HashMap<>();
//        header.put("Cache-Mode", CacheMode.LOAD_CACHE_ELSE_NETWORK);
        header.put("Cache-Mode", CacheMode.LOAD_NETWORK_ELSE_CACHE);
        header.put("Cache-Control", "max-age=360000");
        okhttp3.Request request = OKHttp3RequestFactory.createGetRequest(url, header);
        return EasyHttpUtils.getInstance().executeHttpRequestToCall(request, presenter.getDeliverResultType());
    }
    /**
     * 先网络后缓存
     *
     * @param url
     * @param type
     * @param <T>
     * @return
     */
    public static <T> EasyCall<T> creatGetCall(String url, Type type) {
        HashMap<String, String> header = new HashMap<>();
//        header.put("Cache-Mode", CacheMode.LOAD_CACHE_ELSE_NETWORK);
        header.put("Cache-Mode", CacheMode.LOAD_NETWORK_ELSE_CACHE);
        header.put("Cache-Control", "max-age=360000");
        okhttp3.Request request = OKHttp3RequestFactory.createGetRequest(url, header);
        return EasyHttpUtils.getInstance().executeHttpRequestToCall(request, type);
    }


    /**
     * 先缓存后网络
     *
     * @param url
     * @param presenter
     * @param <T>
     * @return
     */
    public static <T> EasyCall<T> creatGetCallFirstCache(String url, EasyWorkPresenter<T> presenter) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Cache-Mode", CacheMode.LOAD_CACHE_ELSE_NETWORK);
        header.put("Cache-Control", "max-age=340000");
        okhttp3.Request request = OKHttp3RequestFactory.createGetRequest(url, header);
        return EasyHttpUtils.getInstance().executeHttpRequestToCall(request, presenter.getDeliverResultType());
    }
//    public static void postAsyn(String url, Map<String, File> files, Map<String, String> params, Callback responseCallback) {
//        EasyHttpUtils.getInstance().getOkHttpUpLoadUtil().postAsyn(url, files, params, responseCallback);
//    }

    /**
     * 上传文件
     *
     * @param url
     * @param header
     * @param params
     * @param files
     * @param presenter
     * @param <T>
     * @return
     */
    public static <T> EasyCall<T> creatGetCallUpLoadCall(String url, Map<String, String> header, Map<String, String> params, Map<String, File> files, EasyWorkPresenter<T> presenter) {
        okhttp3.Request request = OKHttp3RequestFactory.createMultipartRequest(url, header, params, files);
        return EasyHttpUtils.getInstance().executeHttpRequestToCall(request, presenter.getDeliverResultType());
    }
}
