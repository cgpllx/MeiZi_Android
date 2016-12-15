package com.meizitu.ui.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import cc.easyandroid.easyrecyclerview.EasyRecycleViewDivider;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyrecyclerview.core.DefaultFooterHander;
import cc.easyandroid.easyrecyclerview.core.DefaultHeaderHander;


public class SimpleRecyclerView extends EasyRecyclerView {
    public SimpleRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setHeaderHander(new DefaultHeaderHander(getContext()));
        setFooterHander(new DefaultFooterHander(getContext()));
        setLayoutManager(new LinearLayoutManager(context));
        setRestItemCountToLoadMore(40);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public void setupDefaultDivider() {
//        addItemDecoration(new EasyRecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL).setNotShowDividerCount(1, 1));
    }

    int currentPage;

    public void flipPage() {
        currentPage++;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void resetPage() {
        currentPage = 0;
    }
}
