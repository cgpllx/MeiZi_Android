package com.meizitu.items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.meizitu.R;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.ui.activitys.ImageDetailsActivity;
import com.meizitu.utils.ImageUtils;

import java.util.List;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easymvp.PresenterLoader;
import cc.easyandroid.easymvp.call.EasyThreadCall;
import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.holders.FlexibleViewHolder;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyui.utils.EasyViewUtil;


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
