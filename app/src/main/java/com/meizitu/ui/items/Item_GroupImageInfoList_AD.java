package com.meizitu.ui.items;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.NativeExpressAdView;
import com.meizitu.R;
import com.meizitu.pojo.GroupImageInfo;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.holders.FlexibleViewHolder;
import cc.easyandroid.easyrecyclerview.items.IFlexible;


@SuppressLint("ParcelCreator")
public class Item_GroupImageInfoList_AD extends GroupImageInfo implements IFlexible<Item_GroupImageInfoList_AD.ViewHolder> {

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

    NativeExpressAdView view;

    public Item_GroupImageInfoList_AD(NativeExpressAdView view) {
        this.view = view;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_groupimageinfolist_ad;
    }

    @Override
    public Item_GroupImageInfoList_AD.ViewHolder createViewHolder(EasyFlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(EasyFlexibleAdapter adapter, Item_GroupImageInfoList_AD.ViewHolder holder, int position, List payloads) {
        holder.setData(view);
    }


    public class ViewHolder extends FlexibleViewHolder {
        CardView cardView;
        public ViewHolder(final View view, EasyFlexibleAdapter adapter) {
            super(view, adapter);
            cardView = (CardView) view.findViewById(R.id.cardview);
        }

        public void setData(final NativeExpressAdView mAdView) {
            if (cardView.getChildCount() > 0) {
                cardView.removeAllViews();
            }
            cardView.addView(mAdView);
        }


    }
}
