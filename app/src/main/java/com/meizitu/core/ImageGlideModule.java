package com.meizitu.core;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.meizitu.ImageApplication;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * GlideModule
 */
public class ImageGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
        builder.setMemoryCache(new LruResourceCache(1024 * 1024 * 8))
                .setDiskCache(new InternalCacheDiskCacheFactory(context, 1024 * 1024 * 256));
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
    }

    /**
     * OkHttpClient的cache 会因为glide的缓存导致okhttp缓存失败，所以这里下载图片的时候不使用okhttp缓存
     * @param context
     * @param glide
     */
    @Override
    public void registerComponents(Context context, Glide glide) {
        //小米手机在这里注册没有效果，在app中有注册
        ImageApplication applicationp = (ImageApplication) context.getApplicationContext();
        OkHttpClient okHttpClient= applicationp.getApplicationComponent().getOkHttpClient();
        okHttpClient=okHttpClient.newBuilder().cache(null).build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }
}
