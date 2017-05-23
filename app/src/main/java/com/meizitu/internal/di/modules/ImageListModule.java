package com.meizitu.internal.di.modules;


import com.meizitu.internal.di.PerActivity;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class ImageListModule {

    private int gategoryId = -1;

    public ImageListModule(int gategoryId) {
        this.gategoryId = gategoryId;
    }

    @Provides
    @PerActivity
    @Named("GategoryId")// You can also use Qualifier
    public int provideGategoryId() {
        return gategoryId;
    }

    @Provides
    @PerActivity
    @Named("Time")
    public int provideRequestTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int requestTime = calendar.get(Calendar.DAY_OF_YEAR);
        int convertTime = requestTime % 100;//time 0 - 99
        return convertTime;
    }

}