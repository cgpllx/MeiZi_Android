package com.meizitu;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.meizitu.It.IToggle;
import com.meizitu.items.Item_GroupImageInfoListItem;
import com.meizitu.items.Item_GroupImageInfoList_AD;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyutils.ArrayUtils;

/**
 * Created by chenguoping on 16/10/26.
 */
public class GroupImageInfoListAdapter extends EasyFlexibleAdapter implements IToggle {
    Context context;
    IToggle iToggle;
    public GroupImageInfoListAdapter(Context context,IToggle iToggle) {
        this.context = context;
        this.iToggle = iToggle;
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
        if (!ArrayUtils.isEmpty(items)) {
//            items.add(new Item_GroupImageInfoList_AD());
        }
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

    @Override
    public void closeSingle(Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        iToggle.closeSingle(item_groupImageInfoListItem);
    }

    @Override
    public void openSingle(Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        iToggle.openSingle(item_groupImageInfoListItem);
    }
}
