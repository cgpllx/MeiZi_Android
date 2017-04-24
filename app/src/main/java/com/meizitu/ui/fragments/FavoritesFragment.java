package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.meizitu.R;
import com.meizitu.adapter.GroupImageInfoListAdapter;
import com.meizitu.internal.di.components.FavoritesComponent;
import com.meizitu.mvp.contract.FavoritesListContract;
import com.meizitu.mvp.presenter.FavoritesListPresenter;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;


public class FavoritesFragment extends BaseListFragment<Item_GroupImageInfoListItem> implements FavoritesListContract.View {

    @Inject
    FavoritesListPresenter presenter;

    @Inject
    GroupImageInfoListAdapter adapter;

    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
        setHasOptionsMenu(true);
        getComponent(FavoritesComponent.class).inject(this);
        presenter.attachView(this);
    }

    public static Fragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoListItem> onCreateEasyRecyclerAdapter() {
        return adapter;
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
                    item.setIcon(AnimatedVectorDrawableCompat.create(getContext(), R.drawable.avd_grid_to_list));
                    gridLayoutManager.setSpanCount(2);
                } else {
                    item.setIcon(AnimatedVectorDrawableCompat.create(getContext(), R.drawable.avd_list_to_grid));
                    gridLayoutManager.setSpanCount(1);
                }
                helper.getRecyclerAdapter().notifyItemRangeChanged(1, helper.getRecyclerAdapter().getItemCount());
                break;
        }
        return super.onOptionsItemSelected(item);
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
