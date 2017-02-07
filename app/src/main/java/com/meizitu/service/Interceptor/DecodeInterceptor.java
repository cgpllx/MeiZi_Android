package com.meizitu.service.Interceptor;

import com.meizitu.utils.AESUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by chenguoping on 16/10/23.
 */
public class DecodeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Response response= chain.proceed(originalRequest);
        String  encryption=response.header("encryption");
//        System.out.println("sss encryption ="+encryption);
        if(encryption!=null&&encryption.equals("1")){
            ResponseBody responseBody=response.body();
            try {
                String s=responseBody.string();
//                System.out.println("sss 解密前="+s);
                String   bytes= AESUtil.decode(s);
//                System.out.println("sss="+bytes);
//                System.out.println("sss 解密后="+ bytes);
                return   response.newBuilder().body(ResponseBody.create(responseBody.contentType(),bytes)).build();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return response;
    }
}