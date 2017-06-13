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

import cc.easyandroid.easyrecyclerview.core.progress.EasyProgressFrameLayout;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import uk.co.senab.photoview.PhotoView;

public class BannerImageDetailAdapter<T extends IBanner> extends AbstractViewPagerAdapter<T> {

    public BannerImageDetailAdapter() {
        super();
    }

    @Override
    public View newView(ViewGroup container, int position) {
        View contentView = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_eachpage_layout, null);
        PhotoView imageView = EasyViewUtil.findViewById(contentView, R.id.photoview);
        final EasyProgressFrameLayout progressLayout = EasyViewUtil.findViewById(contentView, R.id.progressView);

        final IBanner banner = getItem(position);
        Glide.with(imageView.getContext())//
                .load(banner.getImageUrl())//
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//
                .placeholder(R.drawable.image_detail_placeholder)//
                .error(R.mipmap.image_detail_load_fail)
                .dontAnimate().into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                progressLayout.showContentView();
                banner.setLoaded(true);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                progressLayout.showContentView();
                banner.setLoaded(true);
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                if (!banner.isLoaded()) {
                    progressLayout.showLoadingView();
                }else{
                    progressLayout.showContentView();
                }
            }
        });
        return contentView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       super.destroyItem(container,position,object);
        ViewGroup contentView = (ViewGroup) object;
        PhotoView imageView = EasyViewUtil.findViewById(contentView, R.id.photoview);
        if (imageView == null)
            return;
        Glide.clear(imageView);     //��oom
    }
}
