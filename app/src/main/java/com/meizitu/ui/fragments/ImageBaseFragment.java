package com.meizitu.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizitu.internal.di.HasComponent;
import com.meizitu.ui.activitys.BaseActivity;

import cc.easyandroid.easyui.fragment.EasyLazyLoadFragment;
import cc.easyandroid.easyutils.EasyToast;

/**
 * fragment的父類
 */
public abstract class ImageBaseFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(getResourceId(), container, false);
    }

    protected abstract int getResourceId();


    public void onError(Object i, Throwable throwable) {
        EasyToast.showShort(getContext(), TextUtils.isEmpty(throwable.getMessage()) ? "服務器或者網絡異常" : throwable.getMessage());
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected Toolbar getToolBar() {
        return ((BaseActivity) getActivity()).getToolbar();
    }

}
