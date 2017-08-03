package com.meizitu.internal.di.modules;


import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.ImageListContract;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class ImageListModule {

    private final ImageListContract.View view;
    private int gategoryId = -1;

    public ImageListModule(int gategoryId, ImageListContract.View view) {
        this.gategoryId = gategoryId;
        this.view = view;
    }

    @Provides
    @PerActivity
    public ImageListContract.View getView() {
        return view;
    }

    @Provides
    @PerActivity
    @Named("CategoryId")// You can also use Qualifier
    public int provideGategoryId() {
        return gategoryId;
    }

}