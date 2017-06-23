package com.meizitu.ui.fragments;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.meizitu.R;
import com.meizitu.banner.BannerImageDetailAdapter;
import com.meizitu.core.DepthPageTransformer;
import com.meizitu.internal.di.components.ImageDetailsComponent;
import com.meizitu.mvp.contract.ImageDetailsContract;
import com.meizitu.mvp.presenter.ImageDetailsPresenter;
import com.meizitu.pojo.ADInfo;
import com.meizitu.pojo.ADInfoProvide;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.Image;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.views.ViewpagerIndicator;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easypermission.EasyPermission;
import cc.easyandroid.easypermission.PermissionListener;
import cc.easyandroid.easypermission.Rationale;
import cc.easyandroid.easypermission.RationaleListener;
import cc.easyandroid.easyrecyclerview.core.progress.EasyProgressFrameLayout;
import cc.easyandroid.easyrecyclerview.listener.OnEasyProgressClickListener;
import cc.easyandroid.easyui.utils.EasyViewUtil;

public class ImageDetailsFragment extends ImageBaseFragment implements ImageDetailsContract.View {

    ViewPager viewPager;

    EasyProgressFrameLayout easyProgress;

    ViewpagerIndicator viewpagerIndicator;

    AdView adView;

    ImageView up;

    ImageView next;

    @Inject
    ImageDetailsPresenter presenter;

    @Inject
    ADInfoProvide adInfoProvide;

    BannerImageDetailAdapter<Image> bannerAdapter;

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


        viewPager = EasyViewUtil.findViewById(view, R.id.banner_viewpager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        easyProgress = EasyViewUtil.findViewById(view, R.id.easyProgress);
        viewpagerIndicator = EasyViewUtil.findViewById(view, R.id.viewpagerIndicator);
        up = EasyViewUtil.findViewById(view, R.id.up, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem - 1);
            }
        });
        next = EasyViewUtil.findViewById(view, R.id.next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem + 1);
            }
        });
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

        initAd(view);
    }

    private void initAd(View view) {
        //----ad
//        adView = EasyViewUtil.findViewById(view, R.id.adView);
//        ADInfo adInfo = null;
//        if (adInfoProvide != null) {
//            adInfo = adInfoProvide.provideADInfo();
//        }
//        if (adInfo != null) {
//            adView = EasyViewUtil.findViewById(view, R.id.adView);
//            adView.setAdUnitId(adInfo.getAd_unit_id_banner());
//            adView.setAdSize(AdSize.BANNER);
//            adView.loadAd(new AdRequest.Builder().build());
//        }
//        adView.loadAd(new AdRequest.Builder().build());

        if (adInfoProvide != null) {
            ADInfo   adInfo = adInfoProvide.provideADInfo();
            if (adInfo != null) {
              ViewGroup avContainer = EasyViewUtil.findViewById(view, R.id.avContainer);
                avContainer.removeAllViews();
                adView=new AdView(view.getContext());
                adView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
                avContainer.addView(adView);
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId(adInfo.getAd_unit_id_banner());
                adView.loadAd(new AdRequest.Builder().build());
            }
        }
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
                bannerAdapter = new BannerImageDetailAdapter();
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
        presenter.initFavoriteMenu(item.getActionView());
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

    public static final int DOWNPERMISSIONREQUESTCODE = 100;
    public static final int SHAREPERMISSIONREQUESTCODE = 200;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detailmenu_down:
                EasyPermission.with(this)//因为回调onRequestPermissionsResult在Fragment或者activity中，所以这里写在activity和Fragment方便处理
                        .requestCode(DOWNPERMISSIONREQUESTCODE)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int i, Rationale rationale) {
                                EasyPermission.rationaleDialog(getContext(), rationale).show();
                            }
                        }).send();
                break;
            case R.id.detailmenu_share:
                EasyPermission.with(this)
                        .requestCode(SHAREPERMISSIONREQUESTCODE)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int i, Rationale rationale) {
                                EasyPermission.rationaleDialog(getContext(), rationale).show();
                            }
                        }).send();
                break;
            case R.id.detailmenu_favorites:
                presenter.handleFavorite(item);
                break;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, List<String> list) {
                if (bannerAdapter != null) {
                    int current = viewPager.getCurrentItem();
                    String imageurl = bannerAdapter.getItem(current).getImageUrl();
                    if (requestCode == DOWNPERMISSIONREQUESTCODE) {
                        presenter.handleDownLoadImage(getActivity(), imageurl);
                    } else if (requestCode == SHAREPERMISSIONREQUESTCODE) {
                        presenter.handleShareImage(getActivity(), imageurl);
                    }
                }
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
                if (EasyPermission.hasAlwaysDeniedPermission(ImageDetailsFragment.this, deniedPermissions)) {
                    EasyPermission.defaultSettingDialog(ImageDetailsFragment.this, requestCode).show();
                }
            }
        });
    }
}
