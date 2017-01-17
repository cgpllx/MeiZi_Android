package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.meizitu.adapter.GroupImageInfoListAdapter;
import com.meizitu.internal.di.components.ImageListComponent;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.mvp.presenter.ImageListPresenter;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;


public class ImageListFragment extends BaseListFragment<Item_GroupImageInfoListItem> implements ImageListContract.View {

    @Inject
    ImageListPresenter presenter;

    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
        getComponent(ImageListComponent.class).inject(this);
        presenter.attachView(this);

    }

    public static Fragment newInstance() {
        ImageListFragment fragment = new ImageListFragment();
        return fragment;
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoListItem> onCreateEasyRecyclerAdapter() {
        return new GroupImageInfoListAdapter(getContext());
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
