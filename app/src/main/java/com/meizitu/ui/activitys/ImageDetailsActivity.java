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
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.ui.fragments.ImageDetailsFragment;

import javax.inject.Inject;

public class ImageDetailsActivity extends BaseActivity implements HasComponent<ImageDetailsComponent> {
    ImageDetailsComponent component;

    public static final String Imagecategory_ID = "Imagecategory_ID";
    public static final String GROUPIMAGEINFO_EXTRA = "GroupImageInfo";

    long DELAYED_TIME = 15 * 1000;

    @Inject
    InterstitialAd mInterstitialAd;

    private long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        this.initializeInjector();
        this.component.inject(this);// inject
        initTitleBar();
        replaceFragment(R.id.content, ImageDetailsFragment.newFragment(), "ImageDetails");

        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("F1AC9E2E84EDE9EFF5C811AA189991B4").build();
            mInterstitialAd.loadAd(adRequest);
        }
        startTime = System.currentTimeMillis();

    }

    private void initializeInjector() {
//        int id = getIntent().getIntExtra(Imagecategory_ID, 0);
        GroupImageInfo groupImageInfo = getIntent().getParcelableExtra(GROUPIMAGEINFO_EXTRA);
        this.component = DaggerImageDetailsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .imageDetailsModule(new ImageDetailsModule(groupImageInfo))
                .activityModule(getActivityModule())
                .build();
    }

    public static Intent newIntent(Context context, GroupImageInfo groupImageInfo) {
        Intent intent = new Intent(context, ImageDetailsActivity.class);
        intent.putExtra(GROUPIMAGEINFO_EXTRA, groupImageInfo);
        return intent;

    }

    @Override
    public void onBackPressed() {
        if (showAd()) {
            showInterstitial();
        }
        super.onBackPressed();
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
