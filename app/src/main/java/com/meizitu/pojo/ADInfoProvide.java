package com.meizitu.pojo;

import android.content.Context;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by cgpllx on 2017/4/24.
 */
public class ADInfoProvide {
    ADInfo adInfo;

    public ADInfoProvide() {
    }

    public ADInfo provideADInfo() {
        return adInfo;
    }

    public void setAdInfo(Context context, ADInfo adInfo) {
        if (adInfo != null) {
            this.adInfo = adInfo;
            MobileAds.initialize(context.getApplicationContext(), adInfo.getAd_unit_id_applicationCode());
        }
    }
}
