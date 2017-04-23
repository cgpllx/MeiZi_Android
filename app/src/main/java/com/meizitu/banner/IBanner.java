package com.meizitu.banner;

/**
 * Created by cgp on 2016/1/25.
 */
public interface IBanner {
    String getImageUrl();

    boolean isLoaded();

    void setLoaded(boolean loaded);
}
