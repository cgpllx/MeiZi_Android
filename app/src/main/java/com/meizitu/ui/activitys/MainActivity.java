package com.meizitu.ui.activitys;


import android.os.Bundle;

import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerImageComponent;
import com.meizitu.internal.di.components.ImageComponent;
import com.meizitu.internal.di.modules.ImageModule;
import com.meizitu.ui.fragments.TabFragment;

import cc.easyandroid.easyutils.EasyToast;


public class MainActivity extends BaseActivity implements HasComponent<ImageComponent> {

    ImageComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitleBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initializeInjector();
        replaceFragment(R.id.fragmentContainer, new TabFragment());
    }

    private void initializeInjector() {
        this.component = DaggerImageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .imageModule(new ImageModule())
                .activityModule(getActivityModule())
                .build();
    }


    private long startTime = 0;

    @Override
    public void onBackPressed() {
        long endTime = System.currentTimeMillis();
        if (startTime > (endTime - 2000)) {
            super.onBackPressed();
        } else {
            startTime = endTime;
            EasyToast.showShort(getApplicationContext(), "再按一次退出");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public ImageComponent getComponent() {
        return component;
    }
}
