/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.meizitu.mvp.presenter;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.UseCaseHandler;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.presentation.presenter.base.EasyBasePresenter;
import cc.easyandroid.easyclean.repository.EasyWorkRepository;
import cc.easyandroid.easylog.EALog;

public class EasyWorkPresenter<T> extends EasyBasePresenter<EasyWorkContract.View<T>> {

    protected final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();
    private final EasyWorkUseCase<T> mEasyWorkUseCase = new EasyWorkUseCase<>(new EasyWorkRepository());


    public EasyWorkPresenter() {
    }

    protected void handleRequest(final EasyWorkUseCase.RequestValues requestValues, UseCase.UseCaseCallback useCaseCallback) {
        mUseCaseHandler.execute(mEasyWorkUseCase, requestValues, useCaseCallback);
    }


    @Override
    public void execute() {

    }

    @Override
    protected void onDetachView() {
        cancel();
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        mEasyWorkUseCase.cancle();
    }

    @Override
    protected void onAttachView(EasyWorkContract.View<T> view) {
        super.onAttachView(view);
    }

}
