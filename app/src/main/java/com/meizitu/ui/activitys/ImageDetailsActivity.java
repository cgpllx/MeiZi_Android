package com.meizitu.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerImageDetailsComponent;
import com.meizitu.internal.di.components.ImageDetailsComponent;
import com.meizitu.internal.di.modules.ImageDetailsModule;
import com.meizitu.ui.fragments.ImageDetailsFragment;

public class ImageDetailsActivity extends BaseActivity implements HasComponent<ImageDetailsComponent> {
    ImageDetailsComponent component;
    public static final String Imagecategory_ID = "Imagecategory_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        this.initializeInjector();
        initTitleBar();
        replaceFragment(R.id.content,ImageDetailsFragment.newFragment(),"ImageDetails");
        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId("ca-app-pub-7086711774077602/4141802409");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
        });
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("F1AC9E2E84EDE9EFF5C811AA189991B4").build();
            mInterstitialAd.loadAd(adRequest);
        }

}

    private void initializeInjector() {
        int id = getIntent().getIntExtra(Imagecategory_ID, 0);
        this.component = DaggerImageDetailsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .imageDetailsModule(new ImageDetailsModule(id))
                .activityModule(getActivityModule())
                .build();

    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, ImageDetailsActivity.class);
        intent.putExtra(Imagecategory_ID, id);
        return intent;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
    }
    private InterstitialAd mInterstitialAd;
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
//            startGame();
        }
    }

    @Override
    public ImageDetailsComponent getComponent() {
        return component;
    }
}
