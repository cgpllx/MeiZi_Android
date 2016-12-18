package com.meizitu;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.ads.MobileAds;
import com.meizitu.internal.di.components.ApplicationComponent;
import com.meizitu.internal.di.components.DaggerApplicationComponent;
import com.meizitu.internal.di.modules.ApplicationModule;

/**
 * app
 */
public class ImageApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary.install(this);
        MobileAds.initialize(this, "ca-app-pub-7086711774077602~7150720809");
        initializeInjector();

    }

    private ApplicationComponent applicationComponent;

    public static ImageApplication get(Context context) {
        return (ImageApplication) context.getApplicationContext();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
