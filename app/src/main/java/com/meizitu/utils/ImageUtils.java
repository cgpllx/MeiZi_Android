package com.meizitu.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.meizitu.ui.views.GlideCircleTransform;
import com.meizitu.ui.views.GlideRoundTransform;

/**
 * 圖片加載工具類
 */
public class ImageUtils {

    public static void clear(ImageView imageView) {
        Glide.clear(imageView);
    }

    public static void load(Context context, ImageView imageView, int resourceId) {
        Glide.with(context).load(resourceId).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void load(Activity context, ImageView imageView, String imageUrl, int placeholder) {
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeholder).into(imageView);
    }

    public static void load(Fragment context, ImageView imageView, String imageUrl, int placeholder) {
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeholder).into(imageView);
    }

    public static void load(FragmentActivity context, ImageView imageView, String imageUrl, int placeholder) {
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeholder).into(imageView);
    }

    public static void loadGlideCircle(FragmentActivity context, ImageView imageView, String imageUrl, int placeholder) {
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeholder).transform(new GlideCircleTransform(context)).into(imageView);
    }

    public static void loadGlideCircle(Context context, ImageView imageView, String imageUrl, int placeholder) {
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeholder).transform(new GlideCircleTransform(context)).into(imageView);
    }

    public static void loadGlideRound(Context context, ImageView imageView, String imageUrl, int placeholder) {
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().transform(new CenterCrop(context), new GlideRoundTransform(context, 4)).placeholder(placeholder).into(imageView);
    }

    public static void load(Context context, ImageView imageView, String imageUrl, int placeholder) {
//        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeholder).dontAnimate().into(imageView);
    }

}
