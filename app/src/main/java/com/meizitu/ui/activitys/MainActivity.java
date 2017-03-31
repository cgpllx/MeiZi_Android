package com.meizitu.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerIndexFragmentComponent;
import com.meizitu.internal.di.components.IndexFragmentComponent;
import com.meizitu.internal.di.modules.IndexFragmentModule;
import com.meizitu.mvp.presenter.MainActivityPresenter;
import com.meizitu.ui.fragments.IndexFragment;

import cc.easyandroid.easyutils.EasyToast;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, HasComponent<IndexFragmentComponent> {
    IndexFragmentComponent component;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    MainActivityPresenter presenter = new MainActivityPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitleBar();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initializeInjector();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, IndexFragment.newInstance()).commitAllowingStateLoss();
    }

    private void initializeInjector() {
        this.component = DaggerIndexFragmentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .indexFragmentModule(new IndexFragmentModule(30))
                .build();
    }

    private long startTime = 0;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long endTime = System.currentTimeMillis();
            if (startTime > (endTime - 2000)) {
                moveTaskToBack(true);
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
    }
}
