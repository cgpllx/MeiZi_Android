package com.meizitu.ui.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.meizitu.R;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easymvp.PresenterLoader;
import cc.easyandroid.easymvp.call.EasyThreadCall;

/**
 * 啟動頁面
 */
public class SplashActivity extends BaseActivity implements EasyWorkContract.View<Long>, PresenterLoader<Long> {
    public static final int STAY_FOR_TIME = 1500;
    public static final Handler handle = new Handler();
    QfangEasyWorkPresenter<Long> presenter = new QfangEasyWorkPresenter<>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        setContentView(R.layout.activity_splash);
        presenter.attachView(this);
        presenter.execute(new EasyWorkUseCase.RequestValues<>("", new EasyThreadCall<>(this), ""));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    protected void startGuideActivity() {
//        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
//        startActivity(intent);
//        this.finish();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onStart(Object o) {
        //not use
    }

    @Override
    public void onSuccess(Object o, Long aBoolean) {
        long delayedTime;//还需延时多少秒
        if (aBoolean > STAY_FOR_TIME) {
            delayedTime = 0;
        } else {
            delayedTime = STAY_FOR_TIME - aBoolean;
        }
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
//                boolean firstStart = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).getBoolean(SpExtraConstant.FIRSTSTART, true);
//                if (firstStart) {
//                    startGuideActivity();
//                } else {
//                    startMainActivity();
//                }
            }
        }, delayedTime);
    }

    @Override
    public Long loadInBackground() throws Exception {
        long startTime = System.currentTimeMillis();
        handleDataThatNeedsToBeDeleted();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static final int APP_CODE = 2;//这里可以控制是否clear数据

    private void handleDataThatNeedsToBeDeleted() {
//        int old_app_code = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).getInt(SpExtraConstant.NEWAPP, 0);
//        if (old_app_code < APP_CODE) {//这里可以控制是否clear数据
//            EasyHttpCache.getInstance().clearCache();
//            PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(SpExtraConstant.NEWAPP, APP_CODE).commit();
//        }
    }
}
