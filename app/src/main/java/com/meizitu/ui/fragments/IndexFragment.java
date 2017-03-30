package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.meizitu.R;
import com.meizitu.adapter.GroupImageInfoListAdapter;
import com.meizitu.internal.di.components.IndexFragmentComponent;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.mvp.presenter.ImageListPresenter;
import com.meizitu.mvp.presenter.IndexFragmentPresenter;
import com.meizitu.pojo.Category;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;


public class IndexFragment extends BaseListFragment<Item_CategoryInfoItem> implements IndexFragmentContract.View {

    @Inject
    IndexFragmentPresenter presenter;

    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
        setHasOptionsMenu(true);
        getComponent(IndexFragmentComponent.class).inject(this);
        presenter.attachView(this);
    }

    public static Fragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    protected EasyFlexibleAdapter<Item_CategoryInfoItem> onCreateEasyRecyclerAdapter() {
        return new GroupImageInfoListAdapter();
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }
}
