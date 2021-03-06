package com.meizitu.ui.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizitu.R;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.holders.FlexibleViewHolder;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyrecyclerview.items.IHeaderSpanFill;

public class Item_Index_nodata implements IFlexible<Item_Index_nodata.ViewHolder> ,IHeaderSpanFill, Parcelable {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean b) {

    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public void setSelectable(boolean b) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_index_nodata;
    }

    @Override
    public Item_Index_nodata.ViewHolder createViewHolder(EasyFlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(EasyFlexibleAdapter adapter, Item_Index_nodata.ViewHolder holder, int position, List payloads) {
    }

    public class ViewHolder extends FlexibleViewHolder {
        public ViewHolder(final View view, EasyFlexibleAdapter adapter) {
            super(view, adapter);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Item_Index_nodata() {
    }

    protected Item_Index_nodata(Parcel in) {
    }

    public static final Creator<Item_Index_nodata> CREATOR = new Creator<Item_Index_nodata>() {
        @Override
        public Item_Index_nodata createFromParcel(Parcel source) {
            return new Item_Index_nodata(source);
        }

        @Override
        public Item_Index_nodata[] newArray(int size) {
            return new Item_Index_nodata[size];
        }
    };
}
