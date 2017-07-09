package com.meizitu.service;

import com.meizitu.pojo.ADInfo;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 接口公共方法
 */
public interface ImageApi {
    //    String DOMAIN = "www.ffvvv.cc";
//    String DOMAIN = "appapi.ffvvv.cc";
    String DOMAIN = "app1.ffvvv.cc";
//    String DOMAIN = "app2.ffvvv.cc";
//    String DOMAIN = "app3.ffvvv.cc";
//    String DOMAIN = "app4.ffvvv.cc";
//        String DOMAIN = "192.168.1.102:8080";
//    String DOMAIN = "192.168.3.9:8080";
//    String DOMAIN = "api.ffvvv.cc";
//    String PATH = "/meizitu";
    String PATH = "";

    //public, max-age=10 请求中带有max-age 响应中也可以有，测试发现是以少的为准，

    /**
     * "Cache-Control: public, max-age=3600
     * http://localhost:8080/meizitu/groupImageInfoList?category=70&page=1&count=3000
     */
//    @Headers({"Cache-Duration:640000", "Cache-Time:36000", "Cache-Control: public"})
    @GET(PATH + "/groupImageInfoList?count=10")
    Call<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> queryGroupImageInfoList(@Query("category") int category, @Query("page") int pageIndex, @Query("time") int requestTime);
    /**
     * http://localhost:8080/meizitu/groupImageInfoDetails?id=2130
     */
//    @Headers({"Cache-Duration:640000", "Cache-Time:36000", "Cache-Control: public"})
    @GET(PATH + "/groupImageInfoDetails")
    Call<ResponseInfo<Item_GroupImageInfoListItem>> queryGroupImageInfoDetails(@Query("id") int id);

//    @Headers({"Cache-Duration:640000", "Cache-Time:36000", "Cache-Control: public"})
    @GET(PATH + "/categoryList")
    Call<ResponseInfo<List<Item_CategoryInfoItem>>> categoryList(@Query("id") int id);

//    @Headers({"Cache-Duration:640000", "Cache-Time:36000", "Cache-Control: public"})
    @GET(PATH + "/groupImageInfoListByNew?count=10")
    Call<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> queryLatestGroupImageList(@Query("page") int pageIndex, @Query("time") int requestTime);

//    @Headers({"Cache-Duration:640000", "Cache-Time:36000", "Cache-Control: public"})
    @GET(PATH + "/adInfo")
    Call<ResponseInfo<ADInfo>> queryAdInfo();
}
