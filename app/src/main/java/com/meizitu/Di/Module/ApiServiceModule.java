package com.meizitu.Di.Module;

import android.app.Application;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.meizitu.service.ImageApi;
import com.meizitu.service.RestApiAdapter;
import com.meizitu.utils.DecodeInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cc.easyandroid.easycache.EasyHttpCache;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.internal.http.BridgeInterceptor;
import retrofit2.Retrofit;

/**
 * Created by clevo on 2015/6/10.
 */
@Module
public class ApiServiceModule {

    @Provides
    @Singleton
    public Retrofit provideRestAdapter(Gson gson, EasyHttpCache cache, OkHttpClient client) {
        return RestApiAdapter.getInstance(gson, cache, client);
    }

    @Provides
    @Singleton
    public ImageApi provideApi(Retrofit restAdapter) {
        return restAdapter.create(ImageApi.class);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public EasyHttpCache provideEasyHttpCache(Application application) {
        return new EasyHttpCache(application);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Application application) {
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(application));
        cookieJar.clear();
        OkHttpClient okHttpClient = (new OkHttpClient.Builder()).connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .cookieJar(cookieJar)
                .addInterceptor(new DecodeInterceptor())//decode
                .addInterceptor(new BridgeInterceptor(cookieJar))//zip
                .build();
        return okHttpClient;
    }
}
