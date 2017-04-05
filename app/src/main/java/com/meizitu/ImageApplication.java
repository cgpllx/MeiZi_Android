package com.meizitu;

import android.app.Application;
import android.content.Context;

import com.antfortune.freeline.FreelineCore;
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
        MobileAds.initialize(this, getString(R.string.ad_unit_id_applicationCode));
        initializeInjector();
        FreelineCore.init(this);

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
