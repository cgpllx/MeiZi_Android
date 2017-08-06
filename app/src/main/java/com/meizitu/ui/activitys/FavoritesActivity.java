package com.meizitu.ui.activitys;

import android.os.Bundle;

import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerFavoritesComponent;
import com.meizitu.internal.di.components.FavoritesComponent;
import com.meizitu.internal.di.modules.FavoritesModule;
import com.meizitu.mvp.contract.FavoritesListContract;
import com.meizitu.ui.fragments.FavoritesFragment;


public class FavoritesActivity extends BaseSwipeBackActivity implements HasComponent<FavoritesComponent> {

    FavoritesComponent favoritesComponent;

    public static final String TAG = FavoritesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        initTitleBar();

        FavoritesFragment fragment = (FavoritesFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            replaceFragment(R.id.content, fragment=FavoritesFragment.newInstance(), TAG);
        }
        initializeInjector(fragment);
    }

    private void initializeInjector(FavoritesListContract.View favoritesView) {
        this.favoritesComponent = DaggerFavoritesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .favoritesModule(new FavoritesModule(favoritesView))
                .build();
    }

    @Override
    public FavoritesComponent getComponent() {
        return favoritesComponent;
    }
}
