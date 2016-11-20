package com.meizitu.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.meizitu.R;
import com.meizitu.ui.views.GlideCircleTransform;
import com.meizitu.ui.views.GlideRoundTransform;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 圖片加載工具類
 */
public class ImageUtils {


    private static final String CACHDIR = "ImgCach";
    private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;
    private static final int MB = 1024 * 1024;
//
//    public static void load(Activity context, ImageView imageView, String imageUrl) {
//        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.qfang_default_small_image).into(imageView);
//    }
//
//    public static void load(Fragment context, ImageView imageView, String imageUrl) {
//        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.qfang_default_small_image).into(imageView);
//    }
//
//    public static void load(FragmentActivity context, ImageView imageView, String imageUrl) {
//        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.qfang_default_small_image).into(imageView);
//    }
//
//    public static void load(Context context, ImageView imageView, String imageUrl) {
//        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.qfang_default_small_image).into(imageView);
//    }

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
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().transform(new CenterCrop(context),new GlideRoundTransform(context, 4)).placeholder(placeholder).into(imageView);
    }

    public static void load(Context context, ImageView imageView, String imageUrl, int placeholder) {
//        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeholder).into(imageView);
    }


    /**
     * 将图片存入文件缓存并返回图片文件的路径
     **/
    public static File saveBitmapAndReturn(Bitmap bm, String url) {
        if (bm == null) {
            return null;
        }
        // 判断sdcard上的空间
        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
            // SD空间不足
            return null;
        }
        String filename = convertUrlToFileName(url);
        String dir = getDirectory();
        File dirFile = new File(dir);
        if (!dirFile.exists())
            dirFile.mkdirs();
        File file = new File(dir + "/" + filename);
        try {
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            Log.w("ImageFileCache", "FileNotFoundException");
        } catch (IOException e) {
            Log.w("ImageFileCache", "IOException");
        }
        return file;
    }

    /**
     * 计算sdcard上的剩余空间
     **/
    private static int freeSpaceOnSd() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
        return (int) sdFreeMB;
    }

    /**
     * 将url转成文件名
     **/
    public static String convertUrlToFileName(String url) {
        // System.out.println("pre disk url:"+url);
        String[] strs = url.split("/");
        String result = strs[strs.length - 2] + "-" + strs[strs.length - 1];
//				+ WHOLESALE_CONV;
        // System.out.println("disk url:"+result);
        return result;
    }

    /**
     * 获得缓存目录
     **/
    private static String getDirectory() {
        String dir = getSDPath() + "/" + CACHDIR;
        return dir;
    }

    /**
     * 取SD卡路径
     **/
    private static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
        }
        if (sdDir != null) {
            return sdDir.toString();
        } else {
            return "";
        }
    }

}
