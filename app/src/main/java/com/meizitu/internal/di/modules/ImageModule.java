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


import com.meizitu.internal.di.PerActivity;
import com.meizitu.service.ImageApi;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import dagger.Module;
import dagger.Provides;
import retrofit2.http.Query;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class ImageModule {
    private int imageid = -1;

    public ImageModule() {
    }

    public ImageModule(int imageid) {
        this.imageid = imageid;
    }

    //
    @Provides
    @PerActivity
    @Named("imageList")
    EasyWorkUseCase.RequestValues provideGetImageListRequestValues(ImageApi imageApi, int category, int pageIndex) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.queryGroupImageInfoList(category, pageIndex));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }

    @Provides
    @PerActivity
    @Named("CategoryList")
    EasyWorkUseCase.RequestValues provideGetCategoryListRequestValues(ImageApi imageApi) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.categoryList(30));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }

    @Provides
    @PerActivity
    @Named("imageDetails")
    EasyWorkUseCase.RequestValues provideGetImageDetailsRequestValues(ImageApi imageApi) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.queryGroupImageInfoDetails(imageid));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }
}