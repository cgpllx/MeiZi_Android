package com.meizitu.ui.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.meizitu.core.EasyFlexibleRecyclerViewHelper;
import com.meizitu.mvp.contract.SimpleListContract;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;

import java.util.ArrayList;
import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyutils.ArrayUtils;

public class BaseListView<T extends IFlexible> extends SimpleRecyclerView implements SimpleListContract.View<ResponseInfo<Paging<List<T>>>> {

    protected EasyFlexibleRecyclerViewHelper<T> helper;

    public BaseListView(Context context) {
        super(context);
        initView(context);
    }

    protected SimpleListContract.Presenter<ResponseInfo<Paging<List<T>>>, SimpleListContract.View<ResponseInfo<Paging<List<T>>>>> presenter;

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }


    private void initView(Context context) {

    }

    public void buildDefaultConfig() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 800;
            }
        };
        setLayoutManager(gridLayoutManager);
        setHasFixedSize(true);
        setAdapter(new EasyFlexibleAdapter<>());
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        helper = new EasyFlexibleRecyclerViewHelper<T>(this, (EasyFlexibleAdapter<T>) adapter) {
            @Override
            public void onRefresh() {
                super.onRefresh();
                refesh();
            }

            @Override
            public void onLoadMore(EasyRecyclerView.FooterHander footerHander) {
                super.onLoadMore(footerHander);
                execute(LOADMORE);
            }

            @Override
            public void onEmptyViewClick() {
                execute(LOADMORE);
            }

            @Override
            public void onErrorViewClick() {
                execute(LOADMORE);
            }
        };
    }

    /**
     * 刷新数据，头部不互动
     */
    protected void refesh() {
        execute(EasyFlexibleRecyclerViewHelper.REFRESH);
    }

    @Override
    public final void autoRefresh() {
        super.autoRefresh();
    }

    protected void execute(int pulltype) {
        presenter.executeSimpleListRequest(pulltype, getCurrentPage() + 1);
    }

    @Override
    public void refeshList() {
        autoRefresh();
    }

    @Override
    public void onSimpleListStart(Object o) {
        showLoadingView();
    }

    @Override
    public void onSimpleListError(Object o, Throwable throwable) {
        if (o != null && o instanceof Integer) {
            Integer type = (Integer) o;
            switch (type.intValue()) {
                case EasyFlexibleRecyclerViewHelper.REFRESH:
                    showErrorView();
                    finishRefresh(false);
                    break;
                case EasyFlexibleRecyclerViewHelper.LOADMORE:
                    finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FAIL);
                    break;
            }
        }
    }

    @Override
    public void onSimpleListSuccess(Object o, ResponseInfo<Paging<List<T>>> pagingResponseInfo) {
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
                    finishRefresh(true);
                    break;
                case EasyFlexibleRecyclerViewHelper.LOADMORE:
                    finishLoadMore(-1);
                    break;
            }
        }
    }

    @Override
    public <P extends SimpleListContract.Presenter> void setPresenter(P presenter) {
        this.presenter = presenter;
    }


    /**
     * 恢复数据
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle savedInstanceState = (Bundle) state;
        super.onRestoreInstanceState(savedInstanceState.getParcelable("super_data"));
        if (savedInstanceState != null) {
            ArrayList list = savedInstanceState.getParcelableArrayList(SAVEDATATAG);
            int firstVisibleItemPosition = savedInstanceState.getInt(FIRSTVISIBLEPOSITION, 0);
            helper.setDatas(list);
            scrollToPosition(firstVisibleItemPosition);
        }
    }

    /**
     * 保存数据
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle outState = new Bundle();
        outState.putParcelable("super_data", super.onSaveInstanceState());
        ArrayList<? extends Parcelable> list = (ArrayList<? extends Parcelable>) helper.getRecyclerAdapter().getItems();
        if (!ArrayUtils.isEmpty(list)) {
            outState.putParcelableArrayList(SAVEDATATAG, list);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            outState.putInt(FIRSTVISIBLEPOSITION, firstVisibleItemPosition);
        }
        return outState;
    }

    final String SAVEDATATAG = "saveDataTAG";

    final String FIRSTVISIBLEPOSITION = "firstVisiblePosition";

}
