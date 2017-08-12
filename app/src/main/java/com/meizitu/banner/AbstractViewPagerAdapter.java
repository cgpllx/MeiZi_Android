package com.meizitu.banner;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cc.easyandroid.easyutils.ArrayUtils;

/**
 * cgpllx1@qq.com
 */
public abstract class AbstractViewPagerAdapter<T extends Parcelable> extends PagerAdapter {
    protected ArrayList<T> mItems=new ArrayList<>();

    public AbstractViewPagerAdapter(ArrayList<T> data) {
        if(!ArrayUtils.isEmpty(data)){
            mItems.addAll(data) ;
        }

    }

    public AbstractViewPagerAdapter() {
        this(null);
    }

    public void setItems(ArrayList<T> data) {
        mItems.clear();
        mItems.addAll(data) ;
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

    @Override
    public Parcelable saveState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable("super_data",super.saveState());
        bundle.putParcelableArrayList("data",mItems);
        return  bundle;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        Bundle bundle= (Bundle) state;
        bundle.setClassLoader(loader);
        super.restoreState(bundle.getParcelable("super_data"), loader);
        ArrayList<T> data=bundle.getParcelableArrayList("data");
        mItems.addAll(data);
        notifyDataSetChanged();
    }
}
