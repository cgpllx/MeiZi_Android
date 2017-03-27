package com.meizitu.banner;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.meizitu.R;

import java.util.ArrayList;

import cc.easyandroid.easyrecyclerview.core.progress.EasyProgressFrameLayout;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import uk.co.senab.photoview.PhotoView;

public class BannerAtlasAdapter<T extends IQfangBanner> extends AbstractViewPagerAdapter<T> {


    public BannerAtlasAdapter(ArrayList<T> data) {
        super(data);
    }

    public BannerAtlasAdapter() {
        super();
    }

    @Override
    public View newView(ViewGroup container, int position) {
        View contentView = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_eachpage_layout, null);
        PhotoView imageView = EasyViewUtil.findViewById(contentView, R.id.photoview);
        final EasyProgressFrameLayout progressLayout = EasyViewUtil.findViewById(contentView, R.id.progressView);
        progressLayout.showLoadingView();
        IQfangBanner qfangBanner = getItem(position);
        Glide.with(container.getContext())//
                .load(qfangBanner.getImageUrl())//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .placeholder(R.drawable.ic_menu_camera)//
                .dontAnimate().into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                progressLayout.showContentView();
                super.onResourceReady(resource, animation);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                progressLayout.showErrorView();
                super.onLoadFailed(e, errorDrawable);
            }
        });
        return contentView;
    }
}
