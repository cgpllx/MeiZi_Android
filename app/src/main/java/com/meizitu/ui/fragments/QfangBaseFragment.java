package com.meizitu.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import cc.easyandroid.easyui.fragment.EasyLazyLoadFragment;

/**
 * fragment的父類
 */
public abstract class QfangBaseFragment extends EasyLazyLoadFragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(getResourceId(), container, false);
    }

    protected abstract int getResourceId();


    protected void lazyLoad() {
    }
}
