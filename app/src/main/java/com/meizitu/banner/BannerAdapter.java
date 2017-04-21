package com.meizitu.banner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.meizitu.R;
import com.meizitu.utils.ImageUtils;

import java.util.ArrayList;

public class BannerAdapter<T extends IBanner> extends AbstractViewPagerAdapter<T> {

//    public BannerAdapter(ArrayList<T> data) {
//        super(data);
//    }

    public BannerAdapter() {
        super();
    }

    @Override
    public View newView(ViewGroup container, final int position) {
        System.out.println("newView position =" + position);
        final ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        IBanner qfangBanner = getItem(position);//需要设置一个tag ，看看是否已经加载过了，加载后的就不用再加载了
        ImageUtils.load(container.getContext(), imageView, qfangBanner.getImageUrl(), R.drawable.image_detail_placeholder);
        if (itemClickListener != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(mItems, position);
                }
            });
        }
        return imageView;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private OnItemClickListener<T> itemClickListener;

    public interface OnItemClickListener<T> {
        void onClick(ArrayList<T> data, int pisition);
    }
}
