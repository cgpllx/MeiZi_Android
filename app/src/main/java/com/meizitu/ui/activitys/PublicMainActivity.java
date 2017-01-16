package com.meizitu.ui.activitys;


import android.os.Bundle;

import com.meizitu.BuildConfig;
import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerImageListComponent;
import com.meizitu.internal.di.components.ImageListComponent;
import com.meizitu.internal.di.modules.ImageListModule;
import com.meizitu.ui.fragments.ListFragment;

import cc.easyandroid.easyutils.EasyToast;


public class PublicMainActivity extends BaseActivity implements HasComponent<ImageListComponent> {

    ImageListComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitleBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initializeInjector();

        replaceFragment(R.id.fragmentContainer, ListFragment.newInstance());
    }

    private void initializeInjector() {
        this.component = DaggerImageListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .imageListModule(new ImageListModule(BuildConfig.GATEGORYID))
                .build();
    }


    private long startTime = 0;

    @Override
    public void onBackPressed() {
        long endTime = System.currentTimeMillis();
        if (startTime > (endTime - 2000)) {
            moveTaskToBack(true);
        } else {
            startTime = endTime;
            EasyToast.showShort(getApplicationContext(), getString(R.string.pressAnotherExit));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public ImageListComponent getComponent() {
        return component;
    }
}
