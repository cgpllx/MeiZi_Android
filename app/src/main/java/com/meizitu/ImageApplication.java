package com.meizitu;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.ExceptionReporter;
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
        initializeInjector();
        Thread.UncaughtExceptionHandler myHandler = new ExceptionReporter(applicationComponent.getTracker(), Thread.getDefaultUncaughtExceptionHandler(), this);
        Thread.setDefaultUncaughtExceptionHandler(myHandler);
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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        Glide.get(this).clearMemory();
    }
}
