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
import com.meizitu.mvp.contract.IndexFragmentContract;

import dagger.Module;
import dagger.Provides;


@Module
public class IndexFragmentModule {

    private final IndexFragmentContract.View view;
    private int id = -1;

    public IndexFragmentModule(int id, IndexFragmentContract.View view) {
        this.id = id;
        this.view = view;
    }

    @Provides
    @PerActivity
    public IndexFragmentContract.View getView() {
        return view;
    }

    @Provides
    @PerActivity
    public int provideGategoryId() {
        return id;
    }


}