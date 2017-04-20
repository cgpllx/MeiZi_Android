package com.meizitu.banner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.meizitu.R;

import java.util.ArrayList;

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

        IQfangBanner qfangBanner = getItem(position);
        Glide.with(container.getContext())//
                .load(qfangBanner.getImageUrl())//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .placeholder(R.drawable.image_detail_placeholder)//
                .dontAnimate().into(imageView);
//        Glide.with(container.getContext()).;//
        return contentView;
    }
}
