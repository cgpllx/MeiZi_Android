package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meizitu.R;
import com.meizitu.core.EasyFlexibleRecyclerViewHelper;
import com.meizitu.mvp.contract.SimpleListContract;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.EasyRecycleViewDivider;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.ArrayUtils;


/**
 * 通用列表
 */
public class CommonFlexibleListFragment<T extends IFlexible> extends QfangBaseFragment implements SimpleListContract.View<QfangResult<PagingResult<T>>> {
    public static final String TAG = CommonFlexibleListFragment.class.getSimpleName();
    protected QfangRecyclerView qfangRecyclerView;
    protected EasyFlexibleRecyclerViewHelper<T> helper;
    protected SimpleListContract.Presenter<QfangResult<PagingResult<T>>, SimpleListContract.View<QfangResult<PagingResult<T>>>> presenter;
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

        qfangRecyclerView = EasyViewUtil.findViewById(view, R.id.qfangRecyclerView);
        qfangRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        qfangRecyclerView.addItemDecoration(onCreateItemDecoration());
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
        if (!isPrepared || !isVisible) {
            return;
        }
        autoRefresh();
    }

    public void autoRefresh() {
        if (qfangRecyclerView != null) {
            qfangRecyclerView.autoRefresh();
        }
    }

    protected void refesh() {
        if (helper != null) {
            execute(EasyFlexibleRecyclerViewHelper.REFRESH);
        }
    }


    private void execute(int pulltype) {
        presenter.executeSimpleListRequest(pulltype, helper.getCurrentPage() + 1);
    }


    protected EasyFlexibleAdapter<T> onCreateEasyRecyclerAdapter() {
        return new EasyFlexibleAdapter<>();
    }

    public void onCompleted(Object o) {
        if (o != null && o instanceof Integer) {
            Integer type = (Integer) o;
            switch (type.intValue()) {
                case EasyRecyclerViewHelper.REFRESH:
                    qfangRecyclerView.finishRefresh(true);
                    break;
                case EasyRecyclerViewHelper.LOADMORE:
                    qfangRecyclerView.finishLoadMore(-1);
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (presenter != null) {
//            presenter.detachView();
//            presenter = null;
//        }
    }

    @Override
    public void setPresenter(SimpleListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void refeshList() {
//        refesh();
        autoRefresh();
    }

    @Override
    public void onSimpleListStart(Object o) {
        qfangRecyclerView.showLoadingView();
    }

    @Override
    public void onSimpleListError(Object o, Throwable t) {
        if (o != null && o instanceof Integer) {
            Integer type = (Integer) o;
            switch (type.intValue()) {
                case EasyRecyclerViewHelper.REFRESH:
                    qfangRecyclerView.showErrorView();
                    qfangRecyclerView.finishRefresh(false);
                    break;
                case EasyRecyclerViewHelper.LOADMORE:
                    qfangRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FAIL);
                    break;
            }
        }
    }

    @Override
    public void onSimpleListSuccess(Object o, QfangResult<PagingResult<T>> pagingResultQfangResult) {
        deliverResult(pagingResultQfangResult);
        onCompleted(o);
    }


    public void deliverResult(QfangResult<PagingResult<T>> pagingResultQfangResult) {
        if (helper.isSuccess(pagingResultQfangResult)) {
            PagingResult<T> pagingResult = pagingResultQfangResult.getData();
            if (pagingResult != null) {
                if ("WAPNOTRESULTREC".equals(pagingResult.getRuleType())) {//没有数据，显示推荐
                    List list = pagingResult.getList();
                    if (!ArrayUtils.isEmpty(list)) {
                        list.add(0, new Item_NoHouse());
                    }
                    helper.setDatas(list);
                } else {
                    helper.setDatas(pagingResult);
                }
            }
        }
    }
}
