package com.meizitu.service;

import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 接口公共方法
 */
public interface ImageApi {
    String DOMAIN = "www.ffvvv.cc";
    //String DOMAIN = "192.168.1.105:8080";

    /**
     * http://localhost:8080/meizitu/groupImageInfoList?category=70&page=1&count=3000
     */
    @Headers({"Cache-Duration: 640000", "Cache-Time: 10000"})
    @GET("/groupImageInfoList?count=20")
    Call<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> queryGroupImageInfoList(@Query("category") int category, @Query("page") int pageIndex, @Header("Cache-Control") String cachecontrol);


    /**
     * http://localhost:8080/meizitu/groupImageInfoDetails?id=2130
     */
    @Headers({"Cache-Duration: 640000", "Cache-Time: 10000"})
    @GET("/groupImageInfoDetails")
    Call<ResponseInfo<Item_GroupImageInfoListItem>> queryGroupImageInfoDetails(@Query("id") int id, @Header("Cache-Control") String cachecontrol);

}
