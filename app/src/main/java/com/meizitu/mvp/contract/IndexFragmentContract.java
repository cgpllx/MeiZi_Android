package com.meizitu.mvp.contract;

import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import cc.easyandroid.easyclean.presentation.presenter.base.EasyIPresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * 屋苑menu的Contract
 */
public interface IndexFragmentContract {
    interface View extends IEasyView ,SimpleListContract.View<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> {
        void onCategoryListStart(Object o);

        void onCategoryListError(Object o, Throwable t);

        void onCategoryListSuccess(Object o, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo);
    }

    interface Presenter extends EasyIPresenter<View> {

    }
}
