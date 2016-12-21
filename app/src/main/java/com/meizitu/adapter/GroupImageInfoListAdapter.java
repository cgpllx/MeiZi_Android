package com.meizitu.adapter;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.meizitu.core.IToggle;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;
import com.meizitu.ui.items.Item_GroupImageInfoList_AD;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyutils.ArrayUtils;

/**
 * Created by chenguoping on 16/10/26.
 */
public class GroupImageInfoListAdapter extends EasyFlexibleAdapter {
    Context context;

    public GroupImageInfoListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public boolean addItems(List items) {
        if (!ArrayUtils.isEmpty(items) && items.size() > 17) {
            NativeExpressAdView nativeExpressAdView = new NativeExpressAdView(context);
            items.add(17, new Item_GroupImageInfoList_AD(nativeExpressAdView));
            handleadrequest(nativeExpressAdView);
        }
        return super.addItems(items);
    }

    @Override
    public void setItems(List items) {
        super.setItems(items);
    }

    private static final String AD_UNIT_ID = "ca-app-pub-7086711774077602/2162118000";

    private void handleadrequest(final NativeExpressAdView mAdView) {
        mAdView.destroy();
        final float density = context.getResources().getDisplayMetrics().density;
        getRecyclerView().post(new Runnable() {
            @Override
            public void run() {
                AdSize adSize = new AdSize((int) (getRecyclerView().getWidth() / density) - 14, 300);/**/
                mAdView.setAdSize(adSize);
                mAdView.setAdUnitId(AD_UNIT_ID);
                mAdView.loadAd(new AdRequest.Builder()
                        //.addTestDevice("F1AC9E2E84EDE9EFF5C811AA189991B4")
                        .build());
            }
        });
    }


}
