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


    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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
    public boolean isEmpty() {
        return getHeaderItemCount()==0;
    }
}
