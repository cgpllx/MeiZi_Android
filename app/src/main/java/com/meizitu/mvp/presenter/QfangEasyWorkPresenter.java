package com.meizitu.mvp.presenter;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.presentation.presenter.EasyWorkPresenter;
import cc.easyandroid.easyclean.repository.EasyWorkRepository;


public class QfangEasyWorkPresenter<T> extends EasyWorkPresenter<T> {
    public QfangEasyWorkPresenter() {
        super((new EasyWorkUseCase<T>(new EasyWorkRepository())));
    }
}
