package com.meizitu.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * cgpllx1@qq.com
 */
public abstract class AbstractViewPagerAdapter<T> extends PagerAdapter {
    protected ArrayList<T> mItems;

    public AbstractViewPagerAdapter(ArrayList<T> data) {
        mItems = data;
    }

    public AbstractViewPagerAdapter() {
        this(new ArrayList<T>());
    }

    public void setItems(ArrayList<T> data) {
        this.mItems = data;
    }

    public void addItems(List<T> data) {
        this.mItems.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    public ArrayList<T> getItems() {
        return mItems;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = newView(container, position);
        container.addView(view);
        return view;
    }

    public abstract View newView(ViewGroup container, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public T getItem(int position) {
        return mItems.get(position);
    }
}
