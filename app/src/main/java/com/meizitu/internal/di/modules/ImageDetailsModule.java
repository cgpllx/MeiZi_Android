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
import com.meizitu.mvp.contract.ImageDetailsContract;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class ImageDetailsModule {

    private final ImageDetailsContract.View view;
    private Item_GroupImageInfoListItem item_groupImageInfoListItem;


    public ImageDetailsModule(Item_GroupImageInfoListItem item_groupImageInfoListItem, ImageDetailsContract.View view) {
        this.item_groupImageInfoListItem = item_groupImageInfoListItem;
        this.view = view;
    }

    @Provides
    @PerActivity
    public ImageDetailsContract.View getView() {
        return view;
    }

    @Provides
    @PerActivity
    public Item_GroupImageInfoListItem provideItem_GroupImageInfoListItem() {
        return item_groupImageInfoListItem;
    }

}