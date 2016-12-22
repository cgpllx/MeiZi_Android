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

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;


public class ImageListFragment extends ImageFlexibleListFragment<Item_GroupImageInfoListItem> implements ImageListContract.View {

    @Inject
    ImageListPresenter presenter;

    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        getComponent(ImageListComponent.class).inject(this);
        presenter.attachView(this);
    }

    public static Fragment newInstance() {
        ImageListFragment fragment = new ImageListFragment();
        fragment.setUserVisibleHint(true);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected EasyWorkUseCase.RequestValues onCreateRequestValues(int pulltype, int pageIndex) {
        return presenter.getRequestValues(pulltype, pageIndex);
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoListItem> onCreateEasyRecyclerAdapter() {
        return new GroupImageInfoListAdapter(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
