package com.meizitu.ui.activitys;


import android.content.Intent;
import android.os.Bundle;



/**
 * 啟動頁面
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        startMainActivity();
    }

    protected void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), PublicMainActivity.class);
        startActivity(intent);
        this.finish();
    }

}
