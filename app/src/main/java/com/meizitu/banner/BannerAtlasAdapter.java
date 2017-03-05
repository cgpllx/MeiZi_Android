package com.meizitu.banner;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.meizitu.R;
import com.meizitu.utils.ImageUtils;


import java.util.ArrayList;

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
        IQfangBanner qfangBanner = getItem(position);
        FrameLayout frameLayout = new FrameLayout(container.getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));
        PhotoView imageView = new PhotoView(container.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setScale(4f);

        frameLayout.addView(imageView);
        ImageUtils.load(container.getContext(), imageView, qfangBanner.getImageUrl(), R.drawable.imagebackground);
//        Glide.with(container.getContext()).load(qfangBanner.getImageUrl()).placeholder(R.mipmap.qfang_default_big_image).dontAnimate().into(imageView);
//     android.R.drawable.ic
        return frameLayout;
    }
}
