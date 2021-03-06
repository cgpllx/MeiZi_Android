package com.meizitu.ui.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.BuildConfig;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdView;
import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerMainActivityComponent;
import com.meizitu.internal.di.components.MainActivityComponent;
import com.meizitu.internal.di.modules.IndexFragmentModule;
import com.meizitu.internal.di.modules.MainActivityModule;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.mvp.contract.MainActivityContract;
import com.meizitu.mvp.presenter.MainActivityPresenter;
import com.meizitu.pojo.ADInfo;
import com.meizitu.pojo.ADInfoProvide;
import com.meizitu.pojo.AppInfo;
import com.meizitu.ui.fragments.IndexFragment;
import com.meizitu.ui.fragments.SplashFragment;
import com.meizitu.ui.fragments.dialog.SimpleDialogFragment;

import javax.inject.Inject;

import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.AppUtils;
import cc.easyandroid.easyutils.EasyToast;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, HasComponent<MainActivityComponent>, MainActivityContract.View {
    MainActivityComponent component;

    DrawerLayout drawer;

    ActionBarDrawerToggle toggle;

    ImageView imageView;

    AdView adView;

    @Inject
    MainActivityPresenter presenter;

    @Inject
    ADInfoProvide adInfoProvide;

    public static final String INDEXFRAGMENT_TAG = "IndexFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, SplashFragment.newInstance()).commitAllowingStateLoss();
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        initTitleBar();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        AppBarLayout appBarLayout= EasyViewUtil.findViewById(this, R.id.app_bar);
        imageView = (ImageView) findViewById(R.id.image);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        IndexFragment fragment = (IndexFragment) getSupportFragmentManager().findFragmentByTag(INDEXFRAGMENT_TAG);
        if (fragment == null) {
            replaceFragment(R.id.content_main, fragment = IndexFragment.newInstance(), INDEXFRAGMENT_TAG);
        }
        initializeInjector(fragment);
        if (savedInstanceState != null) {
            appBarLayout.setExpanded(false);
        }else{
            presenter.executeAppInfoRequest();
        }
        presenter.executeAdInfoRequest();

    }


    private void initializeInjector(IndexFragmentContract.View indexFragmentView) {
        this.component = DaggerMainActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainActivityModule(new MainActivityModule(this))
                .indexFragmentModule(new IndexFragmentModule(40, indexFragmentView))
                .build();
        this.component.inject(this);
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
                if (exitCount == 3) {
                    SimpleDialogFragment.newInstance(getResources().getString(R.string.dialogTitle), getResources().getString(R.string.dialogMessage)).setPositiveButtonOnClickListener(new SimpleDialogFragment.PositiveButtonOnClickListener() {
                        @Override
                        public void positiveOnClick() {
                            presenter.favourableComment(MainActivity.this);
                        }
                    }).show(getSupportFragmentManager(), "tag");
                } else {
                    moveTaskToBack(true);
                }
                if (exitCount <= 3) {
                    sharedPreferences.edit().putInt(CONTROLEVALUATION, exitCount + 1).commit();
                }
            } else {
                EasyToast.showShort(getApplicationContext(), getString(R.string.pressAnotherExit));
            }
            startTime = endTime;
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            // no handle
        } else if (id == R.id.nav_favorites) {
            //TODO: my favorites
            startActivity(new Intent(this, FavoritesActivity.class));
        } else if (id == R.id.nav_score) {
            presenter.favourableComment(this);
        } else if (id == R.id.nav_share) {
            presenter.share(this);
        } else if (id == R.id.nav_feedback) {
            presenter.feedback(this);
        } else if (id == R.id.nav_about_us) {
            presenter.favourableComment(this);
        }
        return true;
    }

    @Override
    public MainActivityComponent getComponent() {
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

    @Override
    public void onAdInfoSuccess(ADInfo adInfo) {
        adInfoProvide.setAdInfo(this,adInfo);
    }

    @Override
    public void onAppInfoSuccess(final AppInfo appInfo) {
        if (appInfo != null) {
            if (appInfo.versionCode > AppUtils.getVersionCode(this)) {//有新版本
                SimpleDialogFragment.newInstance(getResources().getString(R.string.newversion), appInfo.getDescription()).setPositiveButtonOnClickListener(new SimpleDialogFragment.PositiveButtonOnClickListener() {
                    @Override
                    public void positiveOnClick() {
                        presenter.executeDownLoadNewApp(MainActivity.this, appInfo);
                    }
                }).show(getSupportFragmentManager(), "versionCodeFragment");
            }
        }
    }
}
