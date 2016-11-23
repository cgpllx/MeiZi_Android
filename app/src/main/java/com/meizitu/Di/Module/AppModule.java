package com.meizitu.Di.Module;

import android.app.Application;

import javax.inject.Singleton;

import cc.easyandroid.easyhttp.EasyHttpUtils;
import cc.easyandroid.easyhttp.retrofit2.EasyExecutorCallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by clevo on 2015/6/9.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    public Retrofit provideRestAdapter() {
        return new Retrofit.Builder()//
//                .baseUrl("http://" + DOMAIN + "/")//
                .addConverterFactory(GsonConverterFactory.create(EasyHttpUtils.getInstance().getGson()))//
                .addCallAdapterFactory(new EasyExecutorCallAdapterFactory())
                .client(EasyHttpUtils.getInstance().getOkHttpClient())
                .build();

    }
}
