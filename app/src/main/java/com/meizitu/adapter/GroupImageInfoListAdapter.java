package com.meizitu.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.pojo.ADInfo;
import com.meizitu.pojo.ADInfoProvide;
import com.meizitu.ui.items.Item_GroupImageInfoList_AD;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyutils.ArrayUtils;

/**
 * Created by chenguoping on 16/10/26.
 */
@PerActivity
public class GroupImageInfoListAdapter extends EasyFlexibleAdapter {
    ADInfo adInfo;

    @Inject
    public GroupImageInfoListAdapter(ADInfoProvide adInfoProvide) {
        this.adInfo = adInfoProvide.provideADInfo();
    }

    @Override
    public boolean addItems(List items) {
        if (!ArrayUtils.isEmpty(items) && items.size() >= 10) {
            if (adInfo != null) {
                items.add(6, new Item_GroupImageInfoList_AD(adInfo));
            }
        }
        return super.addItems(items);
    }

    @Override
    public void setItems(List items) {
        if (!ArrayUtils.isEmpty(items) && items.size() >= 10) {
            if (adInfo != null) {
//                items.add(6, new Item_GroupImageInfoList_AD(adInfo));
            }
        }
        super.setItems(items);
    }


    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(onScrollListener);
    }


    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    };

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
