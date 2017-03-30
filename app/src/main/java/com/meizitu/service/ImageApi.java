package com.meizitu.service;

import com.meizitu.pojo.Category;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_CategoryInfoItem;
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
//    String DOMAIN = "192.168.1.105:8080";

    //public, max-age=10 请求中带有max-age 响应中也可以有，测试发现是以少的为准，
    /**"Cache-Control: public, max-age=3600
     * http://localhost:8080/meizitu/groupImageInfoList?category=70&page=1&count=3000
     */
    @Headers({"Cache-Duration:640000","Cache-Time:36000","Cache-Control: public"})
    @GET("/groupImageInfoList?count=20")
    Call<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> queryGroupImageInfoList(@Query("category") int category, @Query("page") int pageIndex);


    /**
     * http://localhost:8080/meizitu/groupImageInfoDetails?id=2130
     */
    @Headers({"Cache-Duration:640000","Cache-Time:36000","Cache-Control: public"})
    @GET("/groupImageInfoDetails")
    Call<ResponseInfo<Item_GroupImageInfoListItem>> queryGroupImageInfoDetails(@Query("id") int id);

    @Headers({"Cache-Control: max-age=640000"})
    @GET("/categoryList")
    Call<ResponseInfo<List<Item_CategoryInfoItem>>> categoryList(@Query("id") int id);
}
