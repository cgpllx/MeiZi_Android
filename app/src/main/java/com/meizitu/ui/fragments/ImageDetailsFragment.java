package com.meizitu.ui.fragments;


import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.meizitu.R;
import com.meizitu.banner.BannerAtlasAdapter;
import com.meizitu.internal.di.components.ImageComponent;
import com.meizitu.mvp.contract.ImageDetailsContract;
import com.meizitu.mvp.presenter.ImageDetailsPresenter;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.Image;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.views.ViewpagerIndicator;

import java.io.File;

import javax.inject.Inject;

import cc.easyandroid.customview.progress.KProgressLayout;
import cc.easyandroid.customview.progress.core.KProgressClickListener;

import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.EasyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDetailsFragment extends QfangBaseFragment implements ImageDetailsContract.View {
    ViewPager viewPager;
    KProgressLayout progressLayout;
    ViewpagerIndicator viewpagerIndicator;
    AdView adView;
    @Inject
    ImageDetailsPresenter presenter;

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
        this.getComponent(ImageComponent.class).inject(this);
        presenter.attachView(this);
        adView = EasyViewUtil.findViewById(view, R.id.adView);
        viewPager = EasyViewUtil.findViewById(view, R.id.banner_viewpager);
        progressLayout = EasyViewUtil.findViewById(view, R.id.KProgressLayout);
        viewpagerIndicator = EasyViewUtil.findViewById(view, R.id.viewpagerIndicator);

        execute();

        progressLayout.setKProgressClickListener(new KProgressClickListener() {
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

    public void execute() {
        presenter.exeImageDetailsDataRequest();
    }

    @Override
    public void onDownLoadRequestStart(Object o) {
        //开始下载
    }

    @Override
    public void onDownloadSuccess(File imageFile) {
        if (imageFile != null) {
            MediaScannerConnection.scanFile(getContext(), new String[]{imageFile.getAbsolutePath()}, null, null);
            EasyToast.showShort(getContext(), "圖片保存成功，保存路徑爲：" + imageFile.getAbsolutePath());
        }
    }

    @Override
    public void onDownloadError(Object o, Throwable throwable) {
        throwable.printStackTrace();
        EasyToast.showLong(getContext(), "下载出错了");
    }

    @Override
    public void onShare(Object var1, File imageFile) {
        ShareCompat.IntentBuilder.from(getActivity())
                .setType("image/*")//
                .setStream(Uri.fromFile(imageFile))
                .setChooserTitle("分享")
                .startChooser();
    }

    @Override
    public void onShareError(Object var1, Throwable var2) {
        var2.printStackTrace();
        EasyToast.showLong(getContext(), "分享出错了");
    }


    @Override
    public void onStart(Object o) {
        progressLayout.showLoadingView();
    }

    @Override
    public void onError(Object o, Throwable throwable) {
        progressLayout.showErrorView();
    }

    BannerAtlasAdapter<Image> bannerAdapter;

    @Override
    public void onSuccess(Object o, ResponseInfo<GroupImageInfo> groupImageInfoResponseInfo) {

        if (groupImageInfoResponseInfo != null && groupImageInfoResponseInfo.isSuccess()) {
            GroupImageInfo groupImageInfo = groupImageInfoResponseInfo.getData();
            if (groupImageInfo != null) {
                bannerAdapter = new BannerAtlasAdapter();
                viewPager.setOffscreenPageLimit(3);
                bannerAdapter.addItems(groupImageInfo.getImages());
                viewPager.setAdapter(bannerAdapter);
                viewpagerIndicator.setViewPager(viewPager);
                viewpagerIndicator.setTitleName(groupImageInfo.getTitle());
                progressLayout.cancelAll();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.imagedetail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.down:
                if (bannerAdapter != null) {
                    int current = viewPager.getCurrentItem();
                    String imageurl = bannerAdapter.getItem(current).getImageUrl();
                    FutureTarget<File> future = Glide.with(getContext()).load(imageurl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    presenter.exeDownloadRequest(future);
                }
                break;
            case R.id.share:
                if (bannerAdapter != null) {
                    int current = viewPager.getCurrentItem();
                    String imageurl = bannerAdapter.getItem(current).getImageUrl();
                    FutureTarget<File> future = Glide.with(getContext()).load(imageurl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    presenter.exeShare(future);
                }
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
    }
}
