package com.meizitu;

import android.app.Application;
import android.util.Base64;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.android.gms.ads.MobileAds;
import com.meizitu.utils.AESUtil;
import com.meizitu.utils.Base64Util;
import com.meizitu.utils.DecodeInterceptor;
import com.meizitu.utils.EncryptUtil;
import com.meizitu.utils.SimpleCrypto;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import cc.easyandroid.easycache.CacheUtils;
import cc.easyandroid.easycache.EasyHttpCache;
import cc.easyandroid.easyhttp.EasyHttpUtils;
import cc.easyandroid.easyhttp.config.EAConfiguration;
import cc.easyandroid.easyhttp.core.OkHttpClientFactory;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.BridgeInterceptor;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * app
 */
public class ImageApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//         LeakCanary.install(this);
//        CrashHandler.getInstance().init(this); // Useful for debug.
//        EasyHttpCache.getInstance().initialize(this);
        initEasyAndroid();
        //测试时候发现小米手机不能在registerComponents中调用，所以写在这里
        MobileAds.initialize(this, "ca-app-pub-7086711774077602~7150720809");
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(EasyHttpUtils.getInstance().getOkHttpClient()));
    }

    private void initEasyAndroid() {
        /**
         * OkHttpClient必须设置
         * category
         */
//      OkHttpClient client;//=OkHttpClientFactory.getGenericClient(this);
//        Cache okHttpCache = new Cache(CacheUtils.getDiskCacheDir(this.getApplicationContext(), "okhttpcache"), 10485760L);
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        cookieJar.clear();
        OkHttpClient okHttpClient = (new OkHttpClient.Builder()).connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .cookieJar(cookieJar)
//                .addInterceptor(new GzipRequestInterceptor())
//                .addInterceptor(new BridgeInterceptor(cookieJar))
                .addInterceptor(new DecodeInterceptor())
                .addInterceptor(new BridgeInterceptor(cookieJar))
                .build();
        EAConfiguration eaConfiguration = new EAConfiguration.Builder(this).setOkHttpClient(okHttpClient).build();
        EasyHttpUtils.getInstance().init(eaConfiguration);


    }




}
