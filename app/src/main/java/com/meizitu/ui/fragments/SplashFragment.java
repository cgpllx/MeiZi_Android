package com.meizitu.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.meizitu.R;

/**
 * Created by cgpllx on 2017/4/6.
 */
public class SplashFragment extends ImageBaseFragment {

    @Override
    protected int getResourceId() {
        return R.layout.fragment_splash;
    }

    public static SplashFragment newInstance() {
        SplashFragment splashFragment = new SplashFragment();
        return splashFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView splash = (ImageView) view.findViewById(R.id.splashImage);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        splash.setAnimation(animation);

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .remove(SplashFragment.this).commitAllowingStateLoss();
            }
        }, 2000);
    }
}
