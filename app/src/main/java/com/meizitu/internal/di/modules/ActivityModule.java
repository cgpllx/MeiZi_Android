package com.meizitu.internal.di.modules;

import android.app.Activity;


import com.meizitu.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;


/**
 * 父类转哦给你树妖主人的对象
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }


    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
