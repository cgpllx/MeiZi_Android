package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.meizitu.R;
import com.meizitu.core.EasyFlexibleRecyclerViewHelper;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.EasyHttpRequestParaWrap;
import com.meizitu.ui.views.QfangRecyclerView;


import java.util.List;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.EasyRecycleViewDivider;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyui.utils.EasyViewUtil;


/**
 * 通用列表
 */
public abstract class QfangFlexibleListFragment<T extends IFlexible> extends QfangBaseFragment implements EasyWorkContract.View<ResponseInfo<Paging<List<T>>>> {
    public static final String TAG = QfangFlexibleListFragment.class.getSimpleName();
    protected QfangRecyclerView qfangRecyclerView;
    protected QfangEasyWorkPresenter<ResponseInfo<Paging<List<T>>>> presenter = new QfangEasyWorkPresenter<>();//使用clean
    protected EasyFlexibleRecyclerViewHelper<T> helper;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;

    @Override
    protected int getResourceId() {
        return R.layout.fragment_simple_list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onQfangViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        qfangRecyclerView = EasyViewUtil.findViewById(view, R.id.qfangRecyclerView);
        qfangRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        qfangRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        qfangRecyclerView.addItemDecoration(onCreateItemDecoration());
        qfangRecyclerView.setHasFixedSize(true);
//        final EasyRecyclerAdapter<T> adapter = onCreateEasyRecyclerAdapter();
        final EasyFlexibleAdapter<T> adapter = onCreateEasyRecyclerAdapter();
        helper = new EasyFlexibleRecyclerViewHelper<T>(qfangRecyclerView, adapter) {
            @Override
            public void onRefresh() {
                super.onRefresh();
                execute(REFRESH);
            }

            @Override
            public void onLoadMore(EasyRecyclerView.FooterHander footerHander) {
                super.onLoadMore(footerHander);
                execute(LOADMORE);
            }
        };
        isPrepared = true;
        onQfangViewPrepared(view, savedInstanceState);
        lazyLoad();
        System.out.println("isPrepared= onViewCreated" + isPrepared + " isVisible=" + isVisible);
    }

    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {

    }

    protected void onQfangViewPrepared(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    protected RecyclerView.ItemDecoration onCreateItemDecoration() {
        return new EasyRecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL).setNotShowDividerCount(1, 1);
    }


    protected void lazyLoad() {
        System.out.println("isPrepared=" + isPrepared + " isVisible=" + isVisible);
        if (!isPrepared || !isVisible) {
            return;
        }
        autoRefresh();
    }

    public void autoRefresh() {
        qfangRecyclerView.autoRefresh();
    }

    protected void refesh() {
        execute(EasyFlexibleRecyclerViewHelper.REFRESH);
    }

    public void execute(EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<T>>>> requestValues) {
        presenter.setRequestValues(requestValues);
        presenter.execute();
    }

    private void execute(int pulltype) {
        Bundle paraBundle = EasyHttpRequestParaWrap.getHttpParaFromFragment(this);
        EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<T>>>> requestValues = onCreateRequestValues(pulltype, paraBundle);
        presenter.execute(requestValues);
    }

    protected abstract EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<T>>>> onCreateRequestValues(int pulltype, Bundle paraBundle);

//    protected abstract EasyFlexibleAdapter<T> onCreateEasyRecyclerAdapter();

    @Override
    public void onStart(Object o) {
        qfangRecyclerView.showLoadingView();
    }

    @Override
    public void onError(Object o, Throwable throwable) {
        if (o != null && o instanceof Integer) {
            Integer type = (Integer) o;
            switch (type.intValue()) {
                case EasyFlexibleRecyclerViewHelper.REFRESH:
                    qfangRecyclerView.showErrorView();
                    qfangRecyclerView.finishRefresh(false);
                    break;
                case EasyFlexibleRecyclerViewHelper.LOADMORE:
                    qfangRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FAIL);
                    break;
            }
        }

    }

    @Override
    public void onSuccess(Object o, ResponseInfo<Paging<List<T>>> pagingResultQfangResult) {
        deliverResult(o, pagingResultQfangResult);
        onCompleted(o);
    }

    public void deliverResult(Object i, ResponseInfo<Paging<List<T>>> pagingResultQfangResult) {
        if (helper.isSuccess(pagingResultQfangResult)) {
            Paging<List<T>> pagingResult = pagingResultQfangResult.getData();
            if (pagingResult != null) {
                helper.setDatas(pagingResult);
            }
        }
    }

    protected EasyFlexibleAdapter<T> onCreateEasyRecyclerAdapter() {
        return new EasyFlexibleAdapter<>();
    }

    public void onCompleted(Object o) {
        if (o != null && o instanceof Integer) {
            Integer type = (Integer) o;
            switch (type.intValue()) {
                case EasyFlexibleRecyclerViewHelper.REFRESH:
                    qfangRecyclerView.finishRefresh(true);
                    break;
                case EasyFlexibleRecyclerViewHelper.LOADMORE:
                    qfangRecyclerView.finishLoadMore(-1);
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
