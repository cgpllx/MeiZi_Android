package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.meizitu.R;
import com.meizitu.banner.BannerAtlasAdapter;
import com.meizitu.internal.di.components.ImageDetailsComponent;
import com.meizitu.mvp.contract.ImageDetailsContract;
import com.meizitu.mvp.presenter.ImageDetailsPresenter;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.Image;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.views.ViewpagerIndicator;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.core.progress.EasyProgressFrameLayout;
import cc.easyandroid.easyrecyclerview.listener.OnEasyProgressClickListener;
import cc.easyandroid.easyui.utils.EasyViewUtil;

public class ImageDetailsFragment extends ImageBaseFragment implements ImageDetailsContract.View {
    ViewPager viewPager;

    EasyProgressFrameLayout easyProgress;

    ViewpagerIndicator viewpagerIndicator;

    AdView adView;

    @Inject
    ImageDetailsPresenter presenter;

    BannerAtlasAdapter<Image> bannerAdapter;

    public static Fragment newFragment() {
        ImageDetailsFragment fragment = new ImageDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_image_details;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getComponent(ImageDetailsComponent.class).inject(this);
        presenter.attachView(this);
        adView = EasyViewUtil.findViewById(view, R.id.adView);
        viewPager = EasyViewUtil.findViewById(view, R.id.banner_viewpager);
        easyProgress = EasyViewUtil.findViewById(view, R.id.easyProgress);
        viewpagerIndicator = EasyViewUtil.findViewById(view, R.id.viewpagerIndicator);
        easyProgress.setOnEasyProgressClickListener(new OnEasyProgressClickListener() {
            @Override
            public void onLoadingViewClick() {
            }

            @Override
            public void onEmptyViewClick() {
            }

            @Override
            public void onErrorViewClick() {
                execute();
            }
        });
        adView.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (noData()) {
            execute();
        }

    }

    private boolean noData() {
        return viewPager.getChildCount() <= 0;
    }

    public void execute() {
        presenter.execute();
    }

    @Override
    public void onStart(Object tag) {
        easyProgress.showLoadingView();
    }

    @Override
    public void onError(Throwable throwable) {
        easyProgress.showErrorView();
    }


    @Override
    public void onSuccess(ResponseInfo<GroupImageInfo> groupImageInfoResponseInfo) {
        if (groupImageInfoResponseInfo != null && groupImageInfoResponseInfo.isSuccess()) {
            GroupImageInfo groupImageInfo = groupImageInfoResponseInfo.getData();
            if (groupImageInfo != null) {
                bannerAdapter = new BannerAtlasAdapter();
                viewPager.setOffscreenPageLimit(3);
                bannerAdapter.addItems(groupImageInfo.getImages());
                viewPager.setAdapter(bannerAdapter);
                viewpagerIndicator.setViewPager(viewPager);
                viewpagerIndicator.setTitleName(groupImageInfo.getTitle());
                easyProgress.showContentView();
                showAllMenu(getToolBar().getMenu());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.imagedetail, menu);
        final MenuItem item = menu.findItem(R.id.detailmenu_favorites);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(item);
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        hideAllMenu(menu);
    }

    protected void showAllMenu(Menu menu) {
        if (null != menu) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(true);
            }
        }
    }

    protected void hideAllMenu(Menu menu) {
        if (null != menu) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (bannerAdapter != null) {
            int current = viewPager.getCurrentItem();
            String imageurl = bannerAdapter.getItem(current).getImageUrl();
            presenter.handleNavigationItemSelected(item, getActivity(), imageurl);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adView != null) {
            adView.destroy();
        }
        presenter.detachView();
    }
}
