package com.meizitu.Di.component;

import android.app.Application;

import com.meizitu.Di.Module.ApiServiceModule;
import com.meizitu.Di.Module.AppModule;
import com.meizitu.service.ImageApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by clevo on 2015/6/9.
 */
@Singleton
@Component(modules = {AppModule.class, ApiServiceModule.class})
public interface AppComponent {
    Application getApplication();

    ImageApi getServiceApi();
}
