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


import com.meizitu.internal.di.PerActivity;
import com.meizitu.internal.di.modules.ActivityModule;
import com.meizitu.internal.di.modules.ApplicationModule;
import com.meizitu.internal.di.modules.ImageModule;
import com.meizitu.ui.activitys.LoginActivity;
import com.meizitu.ui.activitys.MainActivity;
import com.meizitu.ui.fragments.ImageDetailsFragment;
import com.meizitu.ui.fragments.ImageListFragment;
import com.meizitu.ui.fragments.TabFragment;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ImageModule.class})
public interface ImageComponent extends ActivityComponent {//只会继承方法

    void inject(ImageDetailsFragment mainActivity);

    void inject(TabFragment mainActivity);

}