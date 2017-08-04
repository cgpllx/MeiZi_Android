package com.meizitu.pojo;

import com.google.gson.annotations.SerializedName;

public class AppInfo {
    @SerializedName("versionName")
    public String versionName;
    @SerializedName("versionCode")
    public int versionCode;
    @SerializedName("apkUrl")
    public String apkUrl;
    @SerializedName("forceUpdate")
    public boolean forceUpdate;
    @SerializedName("description")//To update the content description
    public String description;
    @SerializedName("appName")//To update the content description
    public String appName;

    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public String getDescription() {
        return description;
    }

    public String getAppName() {
        return appName;
    }
}
