package com.meizitu.ui.items;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
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


    public Item_GroupImageInfoList_AD() {
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
        if (!holder.isLoaded) {
            holder.setData();
        }
    }

    public class ViewHolder extends FlexibleViewHolder {
        private ViewGroup containerView;
        private static final String AD_UNIT_ID = "ca-app-pub-7086711774077602/2162118000";
        NativeExpressAdView mAdView;
        private boolean isLoaded = false;

        public ViewHolder(final View view, EasyFlexibleAdapter adapter) {
            super(view, adapter);
            containerView = (ViewGroup) view.findViewById(R.id.containerView);
            if (containerView.getChildCount() <= 0) {
                mAdView = new NativeExpressAdView(getContentView().getContext());
                AdSize adSize = new AdSize(AdSize.FULL_WIDTH, 300);/**/
//                 AdSize adSize = new AdSize(300, 300);/**/
                mAdView.setAdSize(adSize);
                mAdView.setAdUnitId(AD_UNIT_ID);
                containerView.removeAllViews();
                containerView.addView(mAdView);
//                mAdView.loadAd(new AdRequest.Builder().addTestDevice("F1AC9E2E84EDE9EFF5C811AA189991B4").build());
                adapter.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            if (!mAdView.isLoading() && !isLoaded) {
                                isLoaded = true;
                                getContentView().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdView.loadAd(new AdRequest.Builder().addTestDevice("F1AC9E2E84EDE9EFF5C811AA189991B4").build());

                                    }
                                });
                            }else{
                                mAdView.resume();
                            }
                        }else{
                            mAdView.pause();
                        }
                    }
                });
            }

        }

        public synchronized void setData() {
//            mAdView.pause();
//            mAdView.loadAd(new AdRequest.Builder().addTestDevice("F1AC9E2E84EDE9EFF5C811AA189991B4").build());
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected Item_GroupImageInfoList_AD(Parcel in) {
        super(in);
    }

    public static final Creator<Item_GroupImageInfoList_AD> CREATOR = new Creator<Item_GroupImageInfoList_AD>() {
        public Item_GroupImageInfoList_AD createFromParcel(Parcel source) {
            return new Item_GroupImageInfoList_AD(source);
        }

        public Item_GroupImageInfoList_AD[] newArray(int size) {
            return new Item_GroupImageInfoList_AD[size];
        }
    };
}
