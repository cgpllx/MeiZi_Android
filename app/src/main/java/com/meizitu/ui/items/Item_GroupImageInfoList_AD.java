package com.meizitu.ui.items;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.meizitu.R;
import com.meizitu.pojo.ADInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyrecyclerview.holders.FlexibleViewHolder;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyrecyclerview.items.IHeaderSpanFill;
import cc.easyandroid.easyrecyclerview.items.IHolder;


public class Item_GroupImageInfoList_AD implements IFlexible<Item_GroupImageInfoList_AD.ViewHolder>, IHeaderSpanFill, IHolder<ADInfo>, Parcelable {

    final ADInfo adInfo;

    public Item_GroupImageInfoList_AD(ADInfo adInfo) {
        this.adInfo = adInfo;
    }

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
        return R.layout.item_groupimageinfolist_ad;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public Item_GroupImageInfoList_AD.ViewHolder createViewHolder(EasyFlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter, this);
    }

    @Override
    public void bindViewHolder(EasyFlexibleAdapter adapter, Item_GroupImageInfoList_AD.ViewHolder holder, int position, List payloads) {
        // not user
    }

    @Override
    public ADInfo getModel() {
        return adInfo;
    }

    public class ViewHolder extends FlexibleViewHolder {
        private ViewGroup containerView;

        private boolean isLoaded = false;

        public ViewHolder(final View view, EasyFlexibleAdapter adapter, IHolder<ADInfo> iHolder) {
            super(view, adapter);
            final ADInfo adInfo = iHolder.getModel();
            containerView = (ViewGroup) view.findViewById(R.id.containerView);
            if (containerView.getChildCount() <= 0) {
                final NativeExpressAdView mAdView = new NativeExpressAdView(getContentView().getContext());
                containerView.removeAllViews();
                containerView.addView(mAdView);
                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        isLoaded = true;
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        super.onAdFailedToLoad(errorCode);
                    }
                });
                adapter.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE && !mAdView.isLoading() && !isLoaded) {
                            if (recyclerView instanceof EasyRecyclerView) {
                                EasyRecyclerView easyRecyclerView = (EasyRecyclerView) recyclerView;
                                if (!isCanScollVertically(recyclerView) || easyRecyclerView.isRefreshIng() || easyRecyclerView.isLoadIng()) {
                                    return;
                                }
                            }
                            containerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (mAdView.getAdSize() == null) {
                                        AdSize adSize = new AdSize(AdSize.FULL_WIDTH, 300);/**/
                                        mAdView.setAdSize(adSize);
                                        mAdView.setAdUnitId(adInfo.getAd_unit_id_native());
                                    }
                                    try {
                                        mAdView.loadAd(new AdRequest.Builder().addTestDevice("F1AC9E2E84EDE9EFF5C811AA189991B4").build());
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }
                    }
                });
            }

        }

        private boolean isCanScollVertically(RecyclerView recyclerView) {
            return Build.VERSION.SDK_INT >= 14 ? ViewCompat.canScrollVertically(recyclerView, 1) : ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < recyclerView.getHeight();
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.adInfo, flags);
    }

    protected Item_GroupImageInfoList_AD(Parcel in) {
        this.adInfo = in.readParcelable(ADInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<Item_GroupImageInfoList_AD> CREATOR = new Parcelable.Creator<Item_GroupImageInfoList_AD>() {
        @Override
        public Item_GroupImageInfoList_AD createFromParcel(Parcel source) {
            return new Item_GroupImageInfoList_AD(source);
        }

        @Override
        public Item_GroupImageInfoList_AD[] newArray(int size) {
            return new Item_GroupImageInfoList_AD[size];
        }
    };
}
