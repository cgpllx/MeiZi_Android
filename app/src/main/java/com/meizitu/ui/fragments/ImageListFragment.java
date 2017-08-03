package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.meizitu.R;
import com.meizitu.adapter.GroupImageInfoListAdapter;
import com.meizitu.internal.di.components.ImageListComponent;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.mvp.presenter.ImageListPresenter;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;


public class ImageListFragment extends BaseListFragment<Item_GroupImageInfoListItem> implements ImageListContract.View {

    @Inject
    ImageListPresenter mPresenter;

    @Inject
    GroupImageInfoListAdapter mAdapter;

    @Inject
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
        setHasOptionsMenu(true);
        getComponent(ImageListComponent.class).inject(this);
        if (savedInstanceState != null) {
            //presenter.xxx(savedInstanceState);这里要恢复数据
        }
        mPresenter.attachView(this);
    }

    public static ImageListFragment newInstance() {
        ImageListFragment fragment = new ImageListFragment();
        return fragment;
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoListItem> onCreateEasyRecyclerAdapter() {
        mAdapter.initializeListeners(new EasyFlexibleAdapter.OnItemClickListener() {
            @Override
            public boolean onItemClick(int i) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, mPresenter.getCategoryId() + "");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "ItemClick");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                return false;
            }
        });
        return mAdapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.imagelistmenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_to_grid:
                if (gridLayoutManager.getSpanCount() == 1) {
                    item.setIcon(R.drawable.ic_grid_white_24dp);
                    gridLayoutManager.setSpanCount(2);
                } else {
                    item.setIcon(R.drawable.ic_list_white_24dp);
                    gridLayoutManager.setSpanCount(1);
                }
                helper.getRecyclerAdapter().notifyItemRangeChanged(1, helper.getRecyclerAdapter().getItemCount() - 2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }
}
