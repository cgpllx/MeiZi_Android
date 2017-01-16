package com.meizitu.mvp.contract;

import cc.easyandroid.easyclean.presentation.presenter.base.EasyIPresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * Created by chenguoping on 16/12/11.
 */
public interface SimpleListContract {
    interface View<T> extends IEasyView {

        void onSimpleListStart(Object o);

        void onSimpleListError(Object o, Throwable t);

        void onSimpleListSuccess(Object o, T responseInfo);

        <P extends Presenter> void setPresenter(P presenter);

        void refeshList();
    }


    interface Presenter<T, V extends SimpleListContract.View<T>> extends EasyIPresenter<V> {

        void executeSimpleListRequest(int pulltype, int pageIndex);
    }
}
