package com.meizitu.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerImageDetailsComponent;
import com.meizitu.internal.di.components.ImageDetailsComponent;
import com.meizitu.internal.di.modules.ImageDetailsModule;
import com.meizitu.ui.fragments.ImageDetailsFragment;

import javax.inject.Inject;

public class ImageDetailsActivity extends BaseActivity implements HasComponent<ImageDetailsComponent> {
    ImageDetailsComponent component;

    public static final String Imagecategory_ID = "Imagecategory_ID";

    long DELAYED_TIME = 30 * 1000;

    @Inject
    InterstitialAd mInterstitialAd;

    private long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        this.initializeInjector();
        initTitleBar();
        replaceFragment(R.id.content, ImageDetailsFragment.newFragment(), "ImageDetails");

        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("F1AC9E2E84EDE9EFF5C811AA189991B4").build();
            mInterstitialAd.loadAd(adRequest);
        }
        startTime = System.currentTimeMillis();

    }

    private void initializeInjector() {
        int id = getIntent().getIntExtra(Imagecategory_ID, 0);
        this.component = DaggerImageDetailsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .imageDetailsModule(new ImageDetailsModule(id))
                .activityModule(getActivityModule())
                .build();
        this.component.inject(this);// inject
    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, ImageDetailsActivity.class);
        intent.putExtra(Imagecategory_ID, id);
        return intent;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (showAd()) {
            showInterstitial();
        }
    }

    boolean showAd() {
        long endTime = System.currentTimeMillis();
        return endTime - startTime > DELAYED_TIME;
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public ImageDetailsComponent getComponent() {
        return component;//
    }
}
