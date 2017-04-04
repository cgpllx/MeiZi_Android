package com.meizitu.ui.items;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
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
import com.meizitu.pojo.GroupImageInfo;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyrecyclerview.holders.FlexibleViewHolder;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyrecyclerview.items.IHeaderSpanFill;


@SuppressLint("ParcelCreator")
public class Item_GroupImageInfoList_AD extends GroupImageInfo implements IFlexible<Item_GroupImageInfoList_AD.ViewHolder> ,IHeaderSpanFill{

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
        // not user
    }

    public class ViewHolder extends FlexibleViewHolder {
        private ViewGroup containerView;

        private boolean isLoaded = false;

        public ViewHolder(final View view, EasyFlexibleAdapter adapter) {
            super(view, adapter);
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
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            if (!mAdView.isLoading() && !isLoaded) {
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
                                            final float density = getContentView().getContext().getResources().getDisplayMetrics().density;
                                            AdSize adSize = new AdSize((int) (containerView.getWidth() / density) - 4, 300);/**/
                                            mAdView.setAdSize(adSize);
                                            mAdView.setAdUnitId(mAdView.getResources().getString(R.string.ad_unit_id_native));
                                        }
                                        mAdView.loadAd(new AdRequest.Builder().addTestDevice("F1AC9E2E84EDE9EFF5C811AA189991B4").build());
//                                        mAdView.setBackgroundResource(R.color.q8FA3A7);

                                    }
                                });
                            } else {
                                mAdView.resume();
                            }
                        } else {
                            mAdView.pause();
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
