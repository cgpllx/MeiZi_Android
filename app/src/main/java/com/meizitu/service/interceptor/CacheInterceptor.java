package com.meizitu.service.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加缓存
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        CacheControl cacheControl = new CacheControl.Builder()
                .maxStale(30, TimeUnit.SECONDS)
                .build();
        Request request = chain.request();
//        request=request.newBuilder().cacheControl(cacheControl).build();
        Response response = chain.proceed(request);
        String cache = request.header("Cache-Time");
        String dd=response.header("");
        if (!TextUtils.isEmpty(cache)) {//缓存时间不为空
//            Response cacheResponse = response.newBuilder()
//                    .removeHeader("Pragma")
//                    .removeHeader("Cache-Control")
//                    .header("Cache-Control", "max-age=" + cache)    //cache for 10 h
//                    .header("Date", toGMTString())// fix Server time is not allowed to cause the cache is not long
//                    .build();
////            CacheControl cacheControl = new CacheControl.Builder()
////                    .maxStale(30, TimeUnit.SECONDS)
////                    .build();
////
////            Response cacheResponse = response.newBuilder()
////
////                    .build();
////            System.out.println(cache);
////            System.out.println("cgp"+cache);
//            return cacheResponse;
        }
        return response;
    }

    public String toGMTString() {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss 'GMT'", Locale.US);
        TimeZone gmtZone = TimeZone.getTimeZone("GMT");
        sdf.setTimeZone(gmtZone);
        return sdf.format(new Date());
    }
}