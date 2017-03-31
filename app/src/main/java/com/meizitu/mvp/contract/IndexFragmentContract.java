package com.meizitu.mvp.contract;

import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_CategoryInfoItem;

import java.util.List;

import cc.easyandroid.easyclean.presentation.presenter.base.EasyIPresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * 屋苑menu的Contract
 */
public interface IndexFragmentContract {
    interface View extends IEasyView  {
        void onSimpleListStart(Object o);

        void onSimpleListError(Object o, Throwable t);

        void onSimpleListSuccess(Object o, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo);

    }

    interface Presenter extends EasyIPresenter<View> {


    }
}
