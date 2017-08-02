package com.meizitu.ui.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

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
//        setLayoutManager(new LinearLayoutManager(context));
        setRestItemCountToLoadMore(10);
        setOverScrollMode(OVER_SCROLL_NEVER);
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

    static final String CURRENTPAGEKEY = "currentPagekey";

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        super.onRestoreInstanceState(bundle.getParcelable("super_data"));
        currentPage = bundle.getInt(CURRENTPAGEKEY, 0);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("super_data", super.onSaveInstanceState());
        bundle.putInt(CURRENTPAGEKEY, currentPage);
        return bundle;
    }
}
