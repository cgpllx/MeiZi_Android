/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meizitu.internal.di.modules;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.meizitu.ImageApplication;
import com.meizitu.R;
import com.meizitu.service.ImageApi;
import com.meizitu.service.RestApiAdapter;
import com.meizitu.service.Interceptor.CacheInterceptor;
import com.meizitu.service.Interceptor.DecodeInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cc.easyandroid.easycache.EasyHttpCache;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.http.BridgeInterceptor;
import retrofit2.Retrofit;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final ImageApplication application;

    public ApplicationModule(ImageApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

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
    public EasyHttpCache provideEasyHttpCache(Context application) {
        return new EasyHttpCache(application);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Context application) {
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(application));
        cookieJar.clear();
        OkHttpClient okHttpClient = (new OkHttpClient.Builder()).connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .cookieJar(cookieJar)
                .addInterceptor(new DecodeInterceptor())//decode
                .addInterceptor(new BridgeInterceptor(cookieJar))//zip
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(new Cache(application.getExternalCacheDir(), 1024 * 1024 * 10))
                .build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    public InterstitialAd provideInterstitialAd(Context application) {
        InterstitialAd mInterstitialAd = new InterstitialAd(application);
        mInterstitialAd.setAdUnitId(application.getResources().getString(R.string.ad_unit_id_interstitial));
        return mInterstitialAd;
    }

}
