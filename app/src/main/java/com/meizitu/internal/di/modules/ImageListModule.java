package com.meizitu.internal.di.modules;


import com.meizitu.internal.di.PerActivity;

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
    @Named("CategoryId")// You can also use Qualifier
    public int provideGategoryId() {
        return gategoryId;
    }

}