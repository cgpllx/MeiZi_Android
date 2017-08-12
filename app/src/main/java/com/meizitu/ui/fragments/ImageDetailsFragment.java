package com.meizitu.ui.fragments;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.meizitu.ui.views.FixedViewPager;
import com.meizitu.ui.views.ViewpagerIndicator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easypermission.EasyPermission;
import cc.easyandroid.easypermission.PermissionListener;
import cc.easyandroid.easypermission.Rationale;
import cc.easyandroid.easypermission.RationaleListener;
import cc.easyandroid.easyrecyclerview.listener.OnEasyProgressClickListener;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.ArrayUtils;

public class ImageDetailsFragment extends ImageBaseFragment implements ImageDetailsContract.View {

    FixedViewPager viewPager;

    ViewpagerIndicator viewpagerIndicator;

    AdView adView;

    ImageView up;

    ImageView next;

    @Inject
    ImageDetailsPresenter presenter;

    @Inject
    ADInfoProvide adInfoProvide;

    BannerImageDetailAdapter<Image> bannerAdapter;

    public static ImageDetailsFragment newFragment() {
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
        if (savedInstanceState != null) {
            //presenter.xxx(savedInstanceState);这里要恢复数据
        }

        viewPager = EasyViewUtil.findViewById(view, R.id.banner_viewpager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        bannerAdapter = new BannerImageDetailAdapter();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(bannerAdapter);
        viewPager.setOnEasyProgressClickListener(new OnEasyProgressClickListener() {
            @Override
            public void onLoadingViewClick() {

            }

            @Override
            public void onEmptyViewClick() {

            }

            @Override
            public void onErrorViewClick() {
                lazyLoad();
            }
        });
        viewpagerIndicator = EasyViewUtil.findViewById(view, R.id.viewpagerIndicator);
        viewpagerIndicator.setViewPager(viewPager);//和viewpager的pregress有冲突
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

        initAd(view, savedInstanceState);
    }


    private void initAd(View view, Bundle savedInstanceState) {
        //----ad
        if (adInfoProvide != null) {
            ADInfo adInfo = adInfoProvide.provideADInfo();
            if (adInfo == null && savedInstanceState != null) {
                adInfo = savedInstanceState.getParcelable(ADINFOTAG);
                adInfoProvide.setAdInfo(getContext(),adInfo);
            }
            if (adInfo != null) {
                ViewGroup avContainer = EasyViewUtil.findViewById(view, R.id.avContainer);
                avContainer.removeAllViews();
                adView = new AdView(view.getContext());
                adView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                avContainer.addView(adView);
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId(adInfo.getAd_unit_id_banner());
                adView.loadAd(new AdRequest.Builder().build());
            }
        }
    }

    final String ADINFOTAG = "adinfo";//adinfo

    /**
     * 保存数据
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            //adinfo
            if (adInfoProvide != null) {
                ADInfo adInfo = adInfoProvide.provideADInfo();
                if (adInfo != null) {
                    outState.putParcelable(ADINFOTAG, adInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (noData()) {
            lazyLoad();
        }
    }
    protected void lazyLoad() {
        //Resume没有调用 而且setUserVisibleHint没有设置true，都不加载数据
        if (!isResumed() || !getUserVisibleHint()) {
            return;
        }
        execute();
    }
    private boolean noData() {
        return bannerAdapter.getCount() <= 0;
    }

    public void execute() {
        presenter.execute();
    }

    @Override
    public void onStart(Object tag) {
        viewPager.showLoadingView();
    }

    @Override
    public void onError(Throwable throwable) {
        viewPager.showErrorView();
    }


    @Override
    public void onSuccess(ResponseInfo<GroupImageInfo> groupImageInfoResponseInfo) {
        if (groupImageInfoResponseInfo != null && groupImageInfoResponseInfo.isSuccess()) {
            GroupImageInfo groupImageInfo = groupImageInfoResponseInfo.getData();
            if (groupImageInfo != null) {
                bannerAdapter.addItems(groupImageInfo.getImages());
                bannerAdapter.notifyDataSetChanged();

                viewpagerIndicator.setTitleName(groupImageInfo.getTitle());
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
        if (noData()) {
            hideAllMenu(menu);
        }
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
