package com.meizitu.mvp.contract;

import com.meizitu.ui.items.Item_GroupImageInfoListItem;
import com.meizitu.mvp.presenter.SimpleWorkPresenter;
import com.meizitu.pojo.ResponseInfo;

import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * 屋苑menu的Contract
 */
public interface ImageListContract {
    interface View extends IEasyView {

        void onCloseAllStart(Object o);

        void onCloseAllError(Object o, Throwable t);

        void onCloseAllSuccess(Object o, ResponseInfo t);

        //----------------------------------------------------
        void onOpenAllStart(Object o);

        void onOpenAllError(Object o, Throwable t);

        void onOpenAllSuccess(Object o, ResponseInfo t);

        //----------------------------------------------------
        void onCloseSingleStart(Object o);

        void onCloseSingleError(Object o, Throwable t);

        void onCloseSingleSuccess(Item_GroupImageInfoListItem item_groupImageInfoListItem, ResponseInfo t);

        //----------------------------------------------------
        void onOpenSingleStart(Object o);

        void onOpenSingleError(Object o, Throwable t);

        void onOpenSingleSuccess(Item_GroupImageInfoListItem item_groupImageInfoListItem, ResponseInfo t);


    }

    abstract class Presenter extends SimpleWorkPresenter<View> {

        public abstract void exeOpenAllRequest();

        public abstract void exeCloseAllRequest(  );

        //
        public abstract void exeOpenSingleRequest(Item_GroupImageInfoListItem item_groupImageInfoListItem);

        public abstract void exeCloseSingleRequest(Item_GroupImageInfoListItem item_groupImageInfoListItem);


    }
}
