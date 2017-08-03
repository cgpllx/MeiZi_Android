package com.meizitu.internal.di.modules;

import android.app.Activity;


import com.meizitu.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;


/**
 * 这个类没有用，为什么要怎么搞呢���
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
