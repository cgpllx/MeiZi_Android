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
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.meizitu.ImageApplication;
import com.meizitu.mvp.repository.DbRepository;
import com.meizitu.mvp.usecase.DeleteByIdFromDbUseCase;
import com.meizitu.mvp.usecase.GetDataFromDbUseCase;
import com.meizitu.mvp.usecase.GetDatasFromDbUseCase;
import com.meizitu.mvp.usecase.InsertDataFromDbUseCase;
import com.meizitu.pojo.ADInfoProvide;
import com.meizitu.service.ImageApi;
import com.meizitu.service.RestApiAdapter;
import com.meizitu.service.interceptor.DecodeInterceptor;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
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
    public ADInfoProvide provideADInfoProvide() {
        return new ADInfoProvide();
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
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .cookieJar(cookieJar)
                .addInterceptor(new DecodeInterceptor())//decode
                .addNetworkInterceptor(new BridgeInterceptor(cookieJar))
//                .addNetworkInterceptor(new CacheInterceptor())
                .cache(new Cache(new File(application.getCacheDir(), "okhttpcache"), 1024 * 1024 * 10))
                .build();
        return okHttpClient;
    }

//    @Provides
//    @Singleton
//    public InterstitialAd provideInterstitialAd(Context application) {
//        InterstitialAd mInterstitialAd = new InterstitialAd(application);
//        mInterstitialAd.setAdUnitId(application.getResources().getString(R.string.ad_unit_id_interstitial));
//        return mInterstitialAd;
//    }


    @Provides
    @Singleton
    public Tracker provideTracker(Context application) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(application);
        Tracker mTracker = analytics.newTracker("UA-92262187-1");
        mTracker.enableExceptionReporting(true);
        mTracker.enableAutoActivityTracking(true);
        return mTracker;
    }

    @Provides
    @Singleton
    public GetDatasFromDbUseCase<Item_GroupImageInfoListItem> provideGetDatasFromDbUseCase(Context application) {
        return new GetDatasFromDbUseCase<>(new DbRepository(application));
    }

    @Provides
    @Singleton
    public InsertDataFromDbUseCase<Item_GroupImageInfoListItem> provideInsertDataFromDbUseCase(Context application) {
        return new InsertDataFromDbUseCase<>(new DbRepository(application));
    }

    @Provides
    @Singleton
    public DeleteByIdFromDbUseCase provideDeleteByIdFromDbUseCase(Context application) {
        return new DeleteByIdFromDbUseCase(new DbRepository(application));
    }

    @Provides
    @Singleton
    public GetDataFromDbUseCase<Item_GroupImageInfoListItem> provideGetDataFromDbUseCase(Context application) {
        return new GetDataFromDbUseCase<>(new DbRepository(application));
    }

    @Provides
    @Singleton
    @Named("Time")
    public int provideRequestTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int requestTime = calendar.get(Calendar.DAY_OF_YEAR)+20;
        int convertTime = requestTime % 100;//time 0 - 99
        return convertTime;
    }
}
