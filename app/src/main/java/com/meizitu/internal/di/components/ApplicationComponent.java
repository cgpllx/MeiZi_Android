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
package com.meizitu.internal.di.components;

import android.content.Context;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.Tracker;
import com.meizitu.internal.di.modules.ApplicationModule;
import com.meizitu.service.ImageApi;
import com.meizitu.ui.activitys.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {//要注入的对象必须在Component 中现实暴露出来，（或者是构造可以注入）

    void inject(BaseActivity baseActivity);//公用对象都注入到父Activity

    Context context();

    ImageApi getImageApi();//如果其他module要使用，必须在这里暴露

    OkHttpClient getOkHttpClient();

    InterstitialAd getInterstitialAd();

    Tracker getTracker();

}
