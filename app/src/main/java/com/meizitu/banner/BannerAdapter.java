package com.meizitu.banner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.meizitu.R;
import com.meizitu.utils.ImageUtils;

import java.util.ArrayList;

public class BannerAdapter<T extends IQfangBanner> extends AbstractViewPagerAdapter<T> {

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
        IQfangBanner qfangBanner = getItem(position);
        ImageUtils.load(container.getContext(), imageView, qfangBanner.getImageUrl(), R.drawable.imagebackground);
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
