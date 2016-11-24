package com.meizitu;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.meizitu.Di.Module.ApiServiceModule;
import com.meizitu.Di.Module.AppModule;
import com.meizitu.Di.component.AppComponent;
import com.meizitu.Di.component.DaggerAppComponent;

/**
 * app
 */
public class ImageApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//         LeakCanary.install(this);
//        CrashHandler.getInstance().init(this); // Useful for debug.
//        EasyHttpCache.getInstance().initialize(this);
        //测试时候发现小米手机不能在registerComponents中调用，所以写在这里
        MobileAds.initialize(this, "ca-app-pub-7086711774077602~7150720809");
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .build();

    }

    private AppComponent appComponent;

    public static ImageApplication get(Context context) {
        return (ImageApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
