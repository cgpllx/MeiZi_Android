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
    protected void onQfangViewPrepared(View view, Bundle savedInstanceState) {
        super.onQfangViewPrepared(view, savedInstanceState);
//        onQfangViewPrepared(view, savedInstanceState);

//        System.out.println("cgp onViewCreated");
//        if (savedInstanceState != null) {
//            List<Item_GroupImageInfoListItem> list = savedInstanceState.getParcelableArrayList("dd");
//            helper.setDatas(list);
//        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        System.out.println("cgp onSaveInstanceState");
//        ArrayList<Item_GroupImageInfoListItem> list = (ArrayList<Item_GroupImageInfoListItem>) helper.getRecyclerAdapter().getItems();
//        if (!ArrayUtils.isEmpty(list)) {
//            outState.putParcelableArrayList("dd", list);
//            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) simpleRecyclerView.getLayoutManager();
//            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//            outState.putInt("firstVisiblePosition", firstVisibleItemPosition);
//        }
//    }

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
