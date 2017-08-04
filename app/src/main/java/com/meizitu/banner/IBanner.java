package com.meizitu.banner;

import android.os.Parcelable;

/**
 * Created by cgp on 2016/1/25.
 */
public interface IBanner extends Parcelable{
    String getImageUrl();

    boolean isLoaded();

    void setLoaded(boolean loaded);

}
