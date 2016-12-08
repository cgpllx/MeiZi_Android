package com.meizitu.mvp.presenter;



import com.meizitu.internal.di.PerActivity;

import javax.inject.Inject;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.presentation.presenter.EasyWorkPresenter;
import cc.easyandroid.easyclean.repository.EasyWorkRepository;

@PerActivity
public class QfangEasyWorkPresenter<T> extends EasyWorkPresenter<T> {

    @Inject
    public QfangEasyWorkPresenter() {
        super( new EasyWorkUseCase<T>(new EasyWorkRepository() ));
    }
}
