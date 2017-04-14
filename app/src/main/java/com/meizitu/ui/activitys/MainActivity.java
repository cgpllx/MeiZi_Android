package com.meizitu.ui.activitys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.ads.AdView;
import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerIndexFragmentComponent;
import com.meizitu.internal.di.components.IndexFragmentComponent;
import com.meizitu.internal.di.modules.IndexFragmentModule;
import com.meizitu.mvp.presenter.MainActivityPresenter;
import com.meizitu.ui.fragments.IndexFragment;
import com.meizitu.ui.fragments.SplashFragment;
import com.meizitu.ui.fragments.dialog.SimpleDialogFragment;

import cc.easyandroid.easyutils.EasyToast;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, HasComponent<IndexFragmentComponent> {
    IndexFragmentComponent component;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    ImageView imageView;
    AdView adView;
    MainActivityPresenter presenter = new MainActivityPresenter();

    public static final String INDEXFRAGMENT_TAG = "IndexFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, SplashFragment.newInstance()).commitAllowingStateLoss();
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        initTitleBar();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        imageView = (ImageView) findViewById(R.id.image);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initializeInjector();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(INDEXFRAGMENT_TAG);
        if (fragment == null) {
            replaceFragment(R.id.content_main, IndexFragment.newInstance(), INDEXFRAGMENT_TAG);
        }

    }

    private void initializeInjector() {
        this.component = DaggerIndexFragmentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .indexFragmentModule(new IndexFragmentModule(40))
                .build();
    }

    private long startTime = 0;
    public static final String CONTROLEVALUATION = "Controlevaluation";

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long endTime = System.currentTimeMillis();
            if (startTime > (endTime - 2000)) {

                SharedPreferences sharedPreferences = getSharedPreferences(CONTROLEVALUATION, MODE_PRIVATE);
                int exitCount = sharedPreferences.getInt(CONTROLEVALUATION, -1);
                if (exitCount % 3 == 0) {
                    SimpleDialogFragment.newInstance(getResources().getString(R.string.dialogTitle), getResources().getString(R.string.dialogMessage)).show(getSupportFragmentManager(), "tag");
                } else {
                    moveTaskToBack(true);
                }
                if (exitCount <= 9) {
                    sharedPreferences.edit().putInt(CONTROLEVALUATION, exitCount + 1).commit();
                }
            } else {
                startTime = endTime;
                EasyToast.showShort(getApplicationContext(), getString(R.string.pressAnotherExit));
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        presenter.handleNavigationItemSelected(item, this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public IndexFragmentComponent getComponent() {
        return component;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (drawer != null) {
            drawer.removeDrawerListener(toggle);
        }
        if (presenter != null) {
            presenter.detachView();
        }
        if (adView != null) {
            adView.destroy();
        }
    }
}
