package com.meizitu.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyrecyclerview.items.IHeaderSpanFill;

/**
 * Created by chenguoping on 16/10/26.
 */
public class IndexListAdapter extends EasyFlexibleAdapter {


    @Override
    public boolean addItems(List items) {
//        if (!ArrayUtils.isEmpty(items) && items.size() >= 20) {
//            items.add(10, new Item_GroupImageInfoList_AD());
//            items.add(19, new Item_GroupImageInfoList_AD());
//        }
        return super.addItems(items);
    }

    @Override
    public void setItems(List items) {
//        if (!ArrayUtils.isEmpty(items) && items.size() >= 20) {
//            items.add(10, new Item_GroupImageInfoList_AD());
//            items.add(19, new Item_GroupImageInfoList_AD());
//        }
        super.setItems(items);
    }


    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerView.LayoutManager manager1 = recyclerView.getLayoutManager();
        if (manager1 instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager1;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    IFlexible iFlexible = IndexListAdapter.this.getItem(position);

                    if(iFlexible instanceof Item_CategoryInfoItem) {
                        return 2;
                    }
                    return iFlexible != null && iFlexible instanceof IHeaderSpanFill ? gridManager.getSpanCount() : iFlexible instanceof Item_GroupImageInfoListItem ? 3 : 1;
                }
            });
        }
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
    public boolean isEmpty() {
//        return false;
        return getNormalItemCount()<=0;
    }
}
