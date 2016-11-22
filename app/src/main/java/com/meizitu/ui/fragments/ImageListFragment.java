package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.meizitu.GroupImageInfoListAdapter;
import com.meizitu.R;
import com.meizitu.items.Item_GroupImageInfoList;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.mvp.presenter.ImageListPresenter;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.EasyHttpRequestParaWrap;
import com.meizitu.service.QfangRetrofitManager;

import java.util.List;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyutils.EasyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageListFragment extends QfangFlexibleListFragment<Item_GroupImageInfoList> implements ImageListContract.View {

    public static final String ID = "id";

    protected ImageListPresenter presenter = new ImageListPresenter();//使用clean

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
    }

    public static ImageListFragment newInstance() {
        ImageListFragment fragment = new ImageListFragment();
        fragment.setUserVisibleHint(true);
        return fragment;
    }

    @Override
    protected EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoList>>>> onCreateRequestValues(int pulltype, Bundle paraBundle) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(QfangRetrofitManager.getApi().adminGroupImageInfoList(paraBundle.getInt(ID), helper.getCurrentPage() + 1));
        return new EasyWorkUseCase.RequestValues<>(pulltype, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoList> onCreateEasyRecyclerAdapter() {
        return new GroupImageInfoListAdapter(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.imagelist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close:
                close();
                break;
            case R.id.open:
                open();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void close() {
        Bundle paraBundle = EasyHttpRequestParaWrap.getHttpParaFromFragment(this);
        EasyCall easyCall = new RetrofitCallToEasyCall<>(QfangRetrofitManager.getApi().closeGroupImageInfoByCategoryCode(paraBundle.getInt(ID)));
        presenter.exeCloseRequest(new EasyWorkUseCase.RequestValues<>(null, easyCall, null));
    }

    private void open() {
        Bundle paraBundle = EasyHttpRequestParaWrap.getHttpParaFromFragment(this);
        EasyCall easyCall = new RetrofitCallToEasyCall<>(QfangRetrofitManager.getApi().openGroupImageInfoByCategoryCode(paraBundle.getInt(ID)));
        presenter.exeCloseRequest(new EasyWorkUseCase.RequestValues<>(null, easyCall, null));
    }

    @Override
    public void onCloseStart(Object var1) {
        showLoading("正在关闭");
    }

    @Override
    public void onCloseError(Object var1, Throwable var2) {
        onError(var1, var2);
        hideLoading();

    }

    @Override
    public void onCloseSuccess(Object var1, ResponseInfo var2) {
        EasyToast.showShort(getContext(), "onCloseSuccess");
        hideLoading();
    }

    @Override
    public void onOpenStart(Object var1) {
        showLoading("正在打开");
    }

    @Override
    public void onOpenError(Object var1, Throwable var2) {
        onError(var1, var2);
        hideLoading();
    }

    @Override
    public void onOpenSuccess(Object var1, ResponseInfo var2) {
        EasyToast.showShort(getContext(), "onOpenSuccess");
        hideLoading();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
