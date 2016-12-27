package com.meizitu.ui.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**
 * 啟動頁面
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 500);

    }

    protected void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), PublicMainActivity.class);
        startActivity(intent);
        this.finish();
    }

}
