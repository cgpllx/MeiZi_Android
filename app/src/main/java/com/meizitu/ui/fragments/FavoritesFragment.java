package com.meizitu.ui.fragments;


import android.os.Bundle;
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
    protected int getResourceId() {
        return R.layout.fragment_favorites_list;
    }

    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getComponent(FavoritesComponent.class).inject(this);
        if (savedInstanceState != null) {
            //presenter.xxx(savedInstanceState);Data is restored Here
        }
    }

    public static FavoritesFragment newInstance() {
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
                    item.setIcon(R.drawable.ic_grid_white_24dp);
                    gridLayoutManager.setSpanCount(2);
                } else {
                    item.setIcon(R.drawable.ic_list_white_24dp);
                    gridLayoutManager.setSpanCount(1);
                }
                helper.getRecyclerAdapter().notifyItemRangeChanged(1, helper.getRecyclerAdapter().getItemCount()-2);
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
