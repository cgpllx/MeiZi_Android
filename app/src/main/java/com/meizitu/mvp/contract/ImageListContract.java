package com.meizitu.mvp.contract;

import android.app.Activity;
import android.content.Context;

import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import cc.easyandroid.easyclean.presentation.presenter.base.EasyIPresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * 屋苑menu的Contract
 */
public interface ImageListContract {
    interface View extends IEasyView, SimpleListContract.View<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> {

    }

    interface Presenter extends EasyIPresenter<View> {

        void share(Activity activity);
        void feedback(Activity activity);
        void favourablecomment(Context context);
    }
}
