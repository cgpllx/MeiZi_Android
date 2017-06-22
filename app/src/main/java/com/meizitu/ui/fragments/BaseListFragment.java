package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.meizitu.R;
import com.meizitu.core.EasyFlexibleRecyclerViewHelper;
import com.meizitu.mvp.contract.SimpleListContract;
import com.meizitu.pojo.ADInfo;
import com.meizitu.pojo.ADInfoProvide;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.views.SimpleRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.EasyRecycleViewDivider;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.ArrayUtils;
import cc.easyandroid.easyutils.EasyToast;


/**
 * onViewCreated --> onViewStateRestored -->onStart
 * <p/>
 * T 建议实现 Parcelable
 * 通用列表
 */
public class BaseListFragment<T extends IFlexible> extends ImageBaseFragment implements SimpleListContract.View<ResponseInfo<Paging<List<T>>>> {
    public static final String TAG = BaseListFragment.class.getSimpleName();
    protected SimpleRecyclerView simpleRecyclerView;

    protected EasyFlexibleRecyclerViewHelper<T> helper;

    protected GridLayoutManager gridLayoutManager;

    @Inject
    ADInfoProvide adInfoProvide;//Subclasses will be injected if needed

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;

    protected SimpleListContract.Presenter<ResponseInfo<Paging<List<T>>>, SimpleListContract.View<ResponseInfo<Paging<List<T>>>>> presenter;

    @Override
    protected int getResourceId() {
        return R.layout.fragment_simple_list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onQfangViewCreated(view, savedInstanceState);
        simpleRecyclerView = EasyViewUtil.findViewById(view, R.id.qfangRecyclerView);
        gridLayoutManager = new GridLayoutManager(getContext(), 1){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 800;
            }
        };
        simpleRecyclerView.setLayoutManager(gridLayoutManager);
        simpleRecyclerView.setHasFixedSize(true);
        final EasyFlexibleAdapter<T> adapter = onCreateEasyRecyclerAdapter();
        helper = new EasyFlexibleRecyclerViewHelper<T>(simpleRecyclerView, adapter) {
            @Override
            public void onRefresh() {
                super.onRefresh();
//                execute(REFRESH);
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
        isPrepared = true;
        onQfangViewPrepared(view, savedInstanceState);

        if (adInfoProvide != null) {
            ADInfo adInfo = adInfoProvide.provideADInfo();
            if (adInfo != null) {
                avContainer = EasyViewUtil.findViewById(view, R.id.avContainer);
                avContainer.removeAllViews();
                AdView  adView=new AdView(view.getContext());
                adView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId(adInfo.getAd_unit_id_banner());
                avContainer.addView(adView);
                AdRequest adRequest=new AdRequest.Builder().build();
                try{
                    adView.loadAd(adRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }

    }

    ViewGroup avContainer;
    /**
     * 恢复数据
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            if (savedInstanceState != null) {
                ArrayList list = savedInstanceState.getParcelableArrayList(SAVEDATATAG);
                int firstVisibleItemPosition = savedInstanceState.getInt(FIRSTVISIBLEPOSITION, 0);
                helper.setDatas(list);
                simpleRecyclerView.scrollToPosition(firstVisibleItemPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String SAVEDATATAG = "saveDataTAG";
    public static final String FIRSTVISIBLEPOSITION = "firstVisiblePosition";

    /**
     * 保存数据
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            ArrayList<? extends Parcelable> list = (ArrayList<? extends Parcelable>) helper.getRecyclerAdapter().getItems();
            if (!ArrayUtils.isEmpty(list)) {
                outState.putParcelableArrayList(SAVEDATATAG, list);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) simpleRecyclerView.getLayoutManager();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                outState.putInt(FIRSTVISIBLEPOSITION, firstVisibleItemPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (noData()) {
            lazyLoad();
        }
    }

    private boolean noData() {
        return helper.getRecyclerAdapter().getNormalItemCount() <= 0;
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
        if (simpleRecyclerView != null) {
            simpleRecyclerView.autoRefresh();
        }
    }

    protected void refesh() {
        execute(EasyFlexibleRecyclerViewHelper.REFRESH);
    }


    protected void execute(int pulltype) {
        presenter.executeSimpleListRequest(pulltype, helper.getCurrentPage() + 1);
    }


    protected EasyFlexibleAdapter<T> onCreateEasyRecyclerAdapter() {
        return new EasyFlexibleAdapter<>();
    }

    protected void errorTip(Object o, Throwable throwable) {
        EasyToast.showShort(getContext(), throwable != null ? throwable.getMessage() : "服务器或网络异常");
    }

    @Override
    public void onSimpleListStart(Object o) {
        simpleRecyclerView.showLoadingView();
    }

    @Override
    public void onSimpleListError(Object o, Throwable throwable) {
        if (o != null && o instanceof Integer) {
            Integer type = (Integer) o;
            switch (type.intValue()) {
                case EasyFlexibleRecyclerViewHelper.REFRESH:
                    simpleRecyclerView.showErrorView();
                    simpleRecyclerView.finishRefresh(false);
                    break;
                case EasyFlexibleRecyclerViewHelper.LOADMORE:
                    simpleRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FAIL);
                    break;
            }
        }
    }

    @Override
    public void onSimpleListSuccess(Object o, ResponseInfo<Paging<List<T>>> pagingResponseInfo) {
        deliverResult(o, pagingResponseInfo);
        onCompleted(o);
    }

    @Override
    public <P extends SimpleListContract.Presenter> void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void refeshList() {
        autoRefresh();
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
                    simpleRecyclerView.finishRefresh(true);
                    break;
                case EasyFlexibleRecyclerViewHelper.LOADMORE:
                    simpleRecyclerView.finishLoadMore(-1);
                    break;
            }
        }
    }
}
