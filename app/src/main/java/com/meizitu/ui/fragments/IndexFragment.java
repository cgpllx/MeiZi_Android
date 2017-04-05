package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.meizitu.R;
import com.meizitu.core.EasyFlexibleRecyclerViewHelper;
import com.meizitu.internal.di.components.IndexFragmentComponent;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.mvp.presenter.IndexFragmentPresenter;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_Index_LatestImage;
import com.meizitu.ui.views.SimpleRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyui.utils.EasyViewUtil;


public class IndexFragment extends ImageBaseFragment implements IndexFragmentContract.View {
    @Inject
    IndexFragmentPresenter presenter;

    protected SimpleRecyclerView simpleRecyclerView;

    EasyFlexibleAdapter<Item_CategoryInfoItem> adapter = new EasyFlexibleAdapter<>();

    public static Fragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_simple_list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        simpleRecyclerView = EasyViewUtil.findViewById(view, R.id.qfangRecyclerView);
        simpleRecyclerView.setLoadMoreEnabled(false);
        simpleRecyclerView.setRefreshEnabled(false);
//        simpleRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        simpleRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
//        simpleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        simpleRecyclerView.setHasFixedSize(true);

        simpleRecyclerView.setAdapter(adapter);
        setUserVisibleHint(true);
        getComponent(IndexFragmentComponent.class).inject(this);
        presenter.attachView(this);
        presenter.execute();
    }

    @Override
    public void onSimpleListStart(Object o) {
        simpleRecyclerView.showLoadingView();
    }

    @Override
    public void onSimpleListError(Object o, Throwable t) {
        simpleRecyclerView.showErrorView();
    }

    @Override
    public void onSimpleListSuccess(Object o, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        deliverResult(o, responseInfo);
        onCompleted(o);
    }

    public void deliverResult(Object i, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        System.out.println(responseInfo);
        List<Item_CategoryInfoItem> list = responseInfo.getData();
        System.out.println(responseInfo);
        System.out.println(list);
        adapter.addItems(new ArrayList<>(list));
        adapter.addFooterItem(new Item_Index_LatestImage());
    }

    public void onCompleted(Object o) {
        if (o != null && o instanceof Integer) {
            Integer type = (Integer) o;
            switch (type.intValue()) {
                case EasyFlexibleRecyclerViewHelper.REFRESH:
                    simpleRecyclerView.finishRefresh(true);
                    break;
                case EasyFlexibleRecyclerViewHelper.LOADMORE:
                    simpleRecyclerView.finishLoadMore(-1);
                    break;
            }
        }
    }
}
