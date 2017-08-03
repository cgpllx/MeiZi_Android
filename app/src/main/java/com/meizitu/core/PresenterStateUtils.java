package com.meizitu.core;

import android.os.Bundle;

public class PresenterStateUtils {

    public static void saveViewState(Bundle outState, EasyPresenterState easyPresenterState) {
        easyPresenterState.saveInstanceState(outState);
    }


    /**
     * 恢复的Presenter是没有和View绑定的，之后需要自己重新绑定view
     * @param <T> 一般是Presenter
     * @return
     */
    public static <T extends EasyPresenterState> T restoredViewState(Bundle savedInstanceState, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            t.restoreInstanceState(savedInstanceState);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
