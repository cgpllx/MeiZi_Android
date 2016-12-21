package com.meizitu.service;

import com.meizitu.ui.items.Item_GroupImageInfoListItem;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 接口公共方法
 */
public interface ImageApi {
    String DOMAIN = "www.ffvvv.cc";
//    String DOMAIN="192.168.1.105:8080";

    /**
     * http://localhost:8080/meizitu/groupImageInfoList?category=70&page=1&count=3000
     */
    @Headers({"Cache-Control: max-age=640000"})
    @GET("/groupImageInfoList?count=40")
    Call<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> queryGroupImageInfoList(@Query("category") int category, @Query("page") int pageIndex);


    /**
     * http://localhost:8080/meizitu/groupImageInfoDetails?id=2130
     */
    @Headers({"Cache-Control: max-age=640000"})
    @GET("/groupImageInfoDetails")
    Call<ResponseInfo<Item_GroupImageInfoListItem>> queryGroupImageInfoDetails(@Query("id") int id);

}
