package com.meizitu.mvp.contract;

import com.meizitu.pojo.ResponseInfo;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.presentation.presenter.base.EasyBasePresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * 屋苑menu的Contract
 */
public interface ImageListContract {
    interface View extends IEasyView {

        void onCloseStart(Object var1);

        void onCloseError(Object var1, Throwable var2);

        void onCloseSuccess(Object var1, ResponseInfo var2);

        void onOpenStart(Object var1);

        void onOpenError(Object var1, Throwable var2);

        void onOpenSuccess(Object var1, ResponseInfo var2);


    }

    abstract class Presenter extends EasyBasePresenter<View> {

        public abstract void exeOpenRequest(EasyWorkUseCase.RequestValues requestValues);
        public abstract void exeCloseRequest(EasyWorkUseCase.RequestValues requestValues);
//
//        public abstract void exeOpenSingleRequest(EasyWorkUseCase.RequestValues requestValues);
//        public abstract void exeCloseSingleRequest(EasyWorkUseCase.RequestValues requestValues);


    }
}
