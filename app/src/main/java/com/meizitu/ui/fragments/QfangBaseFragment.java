package com.meizitu.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import cc.easyandroid.easyui.fragment.EasyLazyLoadFragment;
import cc.easyandroid.easyutils.EasyToast;

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
    ProgressDialogFragment dialog;

    public void hideLoading() {
        if (dialog != null && dialog.isResumed()) {
            dialog.dismiss();
        }
    }

    public void showLoading(String message) {
        dialog = ProgressDialogFragment.newInstance(message);
        dialog.show(getChildFragmentManager(), "pro");
    }
    public void onError(Object i, Throwable throwable) {
        EasyToast.showShort(getContext(), TextUtils.isEmpty(throwable.getMessage()) ? "服務器或者網絡異常" : throwable.getMessage());

    }
}
