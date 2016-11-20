package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.meizitu.GroupImageInfoListAdapter;
import com.meizitu.items.Item_GroupImageInfoList;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.QfangRetrofitManager;

import java.io.IOException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.EasyHttpUtils;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.BridgeInterceptor;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageListFragment extends QfangFlexibleListFragment<Item_GroupImageInfoList> {

    public static final String ID="id";

    public static ImageListFragment newInstance() {
        ImageListFragment fragment = new ImageListFragment();
        fragment.setUserVisibleHint(true);
        return fragment;
    }

    @Override
    protected EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoList>>>> onCreateRequestValues(int pulltype, Bundle paraBundle) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(QfangRetrofitManager.getApi().queryGroupImageInfoList(paraBundle.getInt(ID), helper.getCurrentPage() + 1));
        return new EasyWorkUseCase.RequestValues<>(pulltype, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoList> onCreateEasyRecyclerAdapter() {
        return new GroupImageInfoListAdapter(getContext());
    }
}
