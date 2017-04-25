package com.meizitu.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cgpllx on 2017/4/24.
 */
public class ADInfo implements Parcelable {
    String ad_unit_id_applicationCode;
    String ad_unit_id_native;
    String ad_unit_id_banner;
    String ad_unit_id_interstitial;

    public String getAd_unit_id_applicationCode() {
        return ad_unit_id_applicationCode;
    }

    public String getAd_unit_id_native() {
        return ad_unit_id_native;
    }

    public String getAd_unit_id_banner() {
        return ad_unit_id_banner;
    }

    public String getAd_unit_id_interstitial() {
        return ad_unit_id_interstitial;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ad_unit_id_applicationCode);
        dest.writeString(this.ad_unit_id_native);
        dest.writeString(this.ad_unit_id_banner);
        dest.writeString(this.ad_unit_id_interstitial);
    }

    public ADInfo() {
    }

    protected ADInfo(Parcel in) {
        this.ad_unit_id_applicationCode = in.readString();
        this.ad_unit_id_native = in.readString();
        this.ad_unit_id_banner = in.readString();
        this.ad_unit_id_interstitial = in.readString();
    }

    public static final Parcelable.Creator<ADInfo> CREATOR = new Parcelable.Creator<ADInfo>() {
        @Override
        public ADInfo createFromParcel(Parcel source) {
            return new ADInfo(source);
        }

        @Override
        public ADInfo[] newArray(int size) {
            return new ADInfo[size];
        }
    };
}
