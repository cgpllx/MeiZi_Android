package com.meizitu.ui.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerFavoritesComponent;
import com.meizitu.internal.di.components.FavoritesComponent;
import com.meizitu.ui.fragments.FavoritesFragment;


public class FavoritesActivity extends BaseSwipeBackActivity implements HasComponent<FavoritesComponent> {
    FavoritesComponent favoritesComponent;
    public static final String TAG = FavoritesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        initTitleBar();
        initializeInjector();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            replaceFragment(R.id.content, FavoritesFragment.newInstance(), TAG);
        }
    }

    private void initializeInjector() {
        this.favoritesComponent = DaggerFavoritesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public FavoritesComponent getComponent() {
        return favoritesComponent;
    }
}
