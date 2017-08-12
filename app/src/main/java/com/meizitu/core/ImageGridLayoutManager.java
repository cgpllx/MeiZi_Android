package com.meizitu.core;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class ImageGridLayoutManager extends GridLayoutManager {

    public ImageGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ImageGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public ImageGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Bundle bundle= (Bundle) state;
        bundle.setClassLoader(getClass().getClassLoader());
        super.onRestoreInstanceState(bundle.getParcelable("super_data"));
        scrollToPosition(bundle.getInt(FIRSTVISIBLEITEMPOSITION_KEY,0));
    }

    public static final String FIRSTVISIBLEITEMPOSITION_KEY="FirstVisibleItemPositionKey";

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("super_data", super.onSaveInstanceState());
        bundle.putInt(FIRSTVISIBLEITEMPOSITION_KEY,findFirstVisibleItemPosition());
        return bundle;
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        return 800;
    }
}
