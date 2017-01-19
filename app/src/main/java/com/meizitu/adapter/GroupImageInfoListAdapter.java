package com.meizitu.adapter;

import android.support.v7.widget.RecyclerView;

import com.meizitu.ui.items.Item_GroupImageInfoList_AD;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyutils.ArrayUtils;

/**
 * Created by chenguoping on 16/10/26.
 */
public class GroupImageInfoListAdapter extends EasyFlexibleAdapter {


    @Override
    public boolean addItems(List items) {
        if (!ArrayUtils.isEmpty(items) && items.size() > 17) {
            items.add(10, new Item_GroupImageInfoList_AD());
        }
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
//        items.add(new Item_GroupImageInfoList_AD());
        return super.addItems(items);
    }

    @Override
    public void setItems(List items) {
        if (!ArrayUtils.isEmpty(items) && items.size() > 17) {
            items.add(10, new Item_GroupImageInfoList_AD());
        }
        super.setItems(items);
    }

    @Override
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
}
