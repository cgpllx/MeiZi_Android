package com.meizitu.ui.activitys;

import android.os.Bundle;

import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerFavoritesComponent;
import com.meizitu.internal.di.components.FavoritesComponent;
import com.meizitu.ui.fragments.FavoritesFragment;

public class FavoritesActivity extends BaseActivity implements HasComponent<FavoritesComponent> {
    FavoritesComponent favoritesComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        initTitleBar();
        initializeInjector();
        replaceFragment(R.id.content, FavoritesFragment.newInstance(), "FavoritesFragment");
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
