package com.meizitu.service;

import com.meizitu.items.Item_GroupImageInfoList;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 接口公共方法
 */
public interface ImageApi {
    /**
     * http://localhost:8080/meizitu/groupImageInfoList?category=70&page=1&count=3000
     */
    @Headers({"Cache-Control: max-age=640000"})
    @GET("/groupImageInfoList?count=40")
    Call<ResponseInfo<Paging<List<Item_GroupImageInfoList>>>> queryGroupImageInfoList(@Query("category") int category, @Query("page") int pageIndex);    /**
     * http://localhost:8080/meizitu/groupImageInfoList?category=70&page=1&count=3000
     */
    @Headers({"Cache-Control: max-age=640000"})
    @GET("/adminGroupImageInfoList?count=40")
    Call<ResponseInfo<Paging<List<Item_GroupImageInfoList>>>> adminGroupImageInfoList(@Query("category") int category, @Query("page") int pageIndex);

    /**
     * http://localhost:8080/meizitu/groupImageInfoDetails?id=2130
     */
    @Headers({"Cache-Control: max-age=640000"})
    @GET("/groupImageInfoDetails")
    Call<ResponseInfo<Item_GroupImageInfoList>> queryGroupImageInfoDetails(@Query("id") int id);

    @Headers({"Cache-Control: max-age=640000"})
    @GET("/categoryList")
    Call<ResponseInfo<List<Category>>> categoryList(@Query("id") int id);

    /**
     * http://localhost:8080/meizitu/groupImageInfoDetails?id=2130
     */
    @GET("/closeGroupImageInfoById")
    Call<ResponseInfo<Item_GroupImageInfoList>> closeGroupImageInfoById(@Query("id") int id);

    @GET("/openGroupImageInfoById")
    Call<ResponseInfo<Item_GroupImageInfoList>> openGroupImageInfoById(@Query("id") int id);

    @GET("/closeGroupImageInfoByCategoryCode")
    Call<ResponseInfo<Item_GroupImageInfoList>> closeGroupImageInfoByCategoryCode(@Query("categoryCode") int id);

    @GET("/openGroupImageInfoByCategoryCode")
    Call<ResponseInfo<Item_GroupImageInfoList>> openGroupImageInfoByCategoryCode(@Query("categoryCode") int id);

    @FormUrlEncoded
    @POST("/login")
    Call<ResponseInfo<Item_GroupImageInfoList>> login(@Field("username") String username, @Field("password") String password);
}
