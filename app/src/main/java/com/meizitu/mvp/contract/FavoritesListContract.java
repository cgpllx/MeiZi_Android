package com.meizitu.mvp.contract;

import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.ArrayList;

import cc.easyandroid.easyclean.presentation.presenter.base.EasyIPresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * Created by chenguoping on 16/12/11.
 */
public interface FavoritesListContract {
    interface View extends IEasyView {

        void onSimpleListStart(Object o);

        void onSimpleListError(Object o, Throwable t);

        void onSimpleListSuccess(Object o, ArrayList<Item_GroupImageInfoListItem> responseInfo);

    }

    interface Presenter extends EasyIPresenter<View> {

    }
}
