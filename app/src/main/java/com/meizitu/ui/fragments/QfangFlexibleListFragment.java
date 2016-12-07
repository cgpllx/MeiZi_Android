package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.meizitu.R;
import com.meizitu.core.EasyFlexibleRecyclerViewHelper;
import com.meizitu.mvp.presenter.SimpleListPresenter;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.views.QfangRecyclerView;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.EasyRecycleViewDivider;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.EasyToast;


/**
 * 通用列表
 */
public class QfangFlexibleListFragment<T extends IFlexible> extends QfangBaseFragment {
    public static final String TAG = QfangFlexibleListFragment.class.getSimpleName();
    protected QfangRecyclerView qfangRecyclerView;

    protected EasyFlexibleRecyclerViewHelper<T> helper;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;

    @Inject
    SimpleListPresenter<T> presenter;

    @Override
    protected int getResourceId() {
        return R.layout.fragment_simple_list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onQfangViewCreated(view, savedInstanceState);
        presenter.attachView(new EasyWorkContract.View<ResponseInfo<Paging<List<T>>>>() {
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
            public void onSuccess(Object o, ResponseInfo<Paging<List<T>>> pagingResponseInfo) {
                deliverResult(o, pagingResponseInfo);
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
        });
        qfangRecyclerView = EasyViewUtil.findViewById(view, R.id.qfangRecyclerView);
        qfangRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        qfangRecyclerView.setHasFixedSize(true);
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
    }

    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {

    }

    protected void onQfangViewPrepared(View view, Bundle savedInstanceState) {

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


    private void execute(int pulltype) {
        presenter.executeRequest(pulltype, helper.getCurrentPage() + 1);
    }


    protected EasyFlexibleAdapter<T> onCreateEasyRecyclerAdapter() {
        return new EasyFlexibleAdapter<>();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    protected void errorTip(Object o, Throwable throwable) {
        EasyToast.showShort(getContext(), throwable != null ? throwable.getMessage() : "服务器或网络异常");
    }
}
