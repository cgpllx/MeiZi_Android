package com.meizitu.ui.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizitu.R;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.holders.FlexibleViewHolder;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyrecyclerview.items.IHeaderSpanFill;

public class Item_Index_HotCategory implements IFlexible<Item_Index_HotCategory.ViewHolder> ,IHeaderSpanFill{

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
        return R.layout.item_index_hot_category;
    }

    @Override
    public Item_Index_HotCategory.ViewHolder createViewHolder(EasyFlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(EasyFlexibleAdapter adapter, Item_Index_HotCategory.ViewHolder holder, int position, List payloads) {
    }

    public class ViewHolder extends FlexibleViewHolder {
        public ViewHolder(final View view, EasyFlexibleAdapter adapter) {
            super(view, adapter);
        }
    }
}
