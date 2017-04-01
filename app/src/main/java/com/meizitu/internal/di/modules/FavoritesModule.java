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

import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.repository.DbRepository;
import com.meizitu.mvp.usecase.GetDatasFromDbUseCase;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class FavoritesModule {
    final GetDatasFromDbUseCase<Item_GroupImageInfoListItem> groupImageInfoListItemGetDatasFromDbUseCase;

    public FavoritesModule(Context context) {
        groupImageInfoListItemGetDatasFromDbUseCase = new GetDatasFromDbUseCase<>(new DbRepository(context));
    }

    @Provides
    @PerActivity
    public GetDatasFromDbUseCase<Item_GroupImageInfoListItem> provideGetDatasFromDbUseCase() {
        return groupImageInfoListItemGetDatasFromDbUseCase;
    }
}