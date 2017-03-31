//package com.meizitu.ui.fragments;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.meizitu.R;
//import com.meizitu.core.CacheControl;
//import com.meizitu.core.EasyFlexibleRecyclerViewHelper;
//import com.meizitu.internal.di.components.IndexFragmentComponent;
//import com.meizitu.mvp.contract.IndexFragmentContract;
//import com.meizitu.mvp.contract.SimpleListContract;
//import com.meizitu.mvp.presenter.IndexFragmentPresenter;
//import com.meizitu.pojo.Paging;
//import com.meizitu.pojo.ResponseInfo;
//import com.meizitu.ui.items.Item_CategoryInfoItem;
//import com.meizitu.ui.views.SimpleRecyclerView;
//
//import java.util.List;
//
//import javax.inject.Inject;
//
//import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
//import cc.easyandroid.easyrecyclerview.EasyRecycleViewDivider;
//import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
//import cc.easyandroid.easyui.utils.EasyViewUtil;
//import cc.easyandroid.easyutils.EasyToast;
//
//
//public class Index1Fragment extends ImageBaseFragment implements IndexFragmentContract.View {
//    public static final String TAG = BaseListFragment.class.getSimpleName();
//    protected SimpleRecyclerView simpleRecyclerView;
//
//    protected EasyFlexibleRecyclerViewHelper<T> helper;
//    /**
//     */
//    private boolean isPrepared;
//
//    protected SimpleListContract.Presenter<ResponseInfo<Paging<List<T>>>, SimpleListContract.View<ResponseInfo<Paging<List<T>>>>> presenter;
//
//    @Override
//    protected int getResourceId() {
//        return R.layout.fragment_simple_list;
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        simpleRecyclerView = EasyViewUtil.findViewById(view, R.id.qfangRecyclerView);
//        //simpleRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        simpleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        simpleRecyclerView.setHasFixedSize(true);
//        final EasyFlexibleAdapter<T> adapter = onCreateEasyRecyclerAdapter();
//        helper = new EasyFlexibleRecyclerViewHelper<T>(simpleRecyclerView, adapter) {
//            @Override
//            public void onRefresh() {
//                super.onRefresh();
//                execute(REFRESH, CacheControl.FORCE_NETWORK);
//            }
//
//            @Override
//            public void onLoadMore(EasyRecyclerView.FooterHander footerHander) {
//                super.onLoadMore(footerHander);
//                execute(LOADMORE, CacheControl.DEFAULT);
//            }
//
//            @Override
//            public void onEmptyViewClick() {
//                execute(LOADMORE, CacheControl.FORCE_NETWORK);
//            }
//
//            @Override
//            public void onErrorViewClick() {
//                execute(LOADMORE, CacheControl.FORCE_NETWORK);
//            }
//        };
//        isPrepared = true;
//        onQfangViewPrepared(view, savedInstanceState);
//    }
//
//
//
//    public static final String SAVEDATATAG = "saveDataTAG";
//    public static final String FIRSTVISIBLEPOSITION = "firstVisiblePosition";
//
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (noData()) {
//            lazyLoad();
//        }
//    }
//
//    private boolean noData() {
//        return helper.getRecyclerAdapter().getNormalItemCount() <= 0;
//    }
//
//
//
//
//    protected RecyclerView.ItemDecoration onCreateItemDecoration() {
//        return new EasyRecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL).setNotShowDividerCount(1, 1);
//    }
//
//
//    protected void lazyLoad() {
//        if (!isPrepared || !isVisible) {
//            return;
//        }
//        autoRefresh();
//    }
//
//    public void autoRefresh() {
//        if (simpleRecyclerView != null) {
//            simpleRecyclerView.autoRefresh();
//        }
//    }
//
////    public static final String FORCE_NETWORK = "no-cache";
////    public static final String DEFAULT="";
//
//    protected void refesh() {
//        execute(EasyFlexibleRecyclerViewHelper.REFRESH, CacheControl.FORCE_NETWORK);
//    }
//
//
//    private void execute(int pulltype, String cachecontrol) {
//        presenter.executeSimpleListRequest(pulltype, helper.getCurrentPage() + 1, cachecontrol);
//    }
//
//
//    protected EasyFlexibleAdapter<T> onCreateEasyRecyclerAdapter() {
//        return new EasyFlexibleAdapter<>();
//    }
//
//    protected void errorTip(Object o, Throwable throwable) {
//    }
//
//    @Override
//    public void onSimpleListStart(Object o) {
//        simpleRecyclerView.showLoadingView();
//    }
//
//    @Override
//    public void onSimpleListError(Object o, Throwable throwable) {
//        if (o != null && o instanceof Integer) {
//            Integer type = (Integer) o;
//            switch (type.intValue()) {
//                case EasyFlexibleRecyclerViewHelper.REFRESH:
//                    simpleRecyclerView.showErrorView();
//                    simpleRecyclerView.finishRefresh(false);
//                    break;
//                case EasyFlexibleRecyclerViewHelper.LOADMORE:
//                    simpleRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FAIL);
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public void onSimpleListSuccess(Object o, ResponseInfo<Paging<List<T>>> pagingResponseInfo) {
//        deliverResult(o, pagingResponseInfo);
//        onCompleted(o);
//    }
//
//    @Override
//    public <P extends SimpleListContract.Presenter> void setPresenter(P presenter) {
//        this.presenter = presenter;
//    }
//
//    @Override
//    public void refeshList() {
//        autoRefresh();
//    }
//
//    public void deliverResult(Object i, ResponseInfo<Paging<List<T>>> pagingResultQfangResult) {
//        if (helper.isSuccess(pagingResultQfangResult)) {
//            Paging<List<T>> pagingResult = pagingResultQfangResult.getData();
//            if (pagingResult != null) {
//                helper.setDatas(pagingResult);
//            }
//        }
//    }
//
//    public void onCompleted(Object o) {
//        if (o != null && o instanceof Integer) {
//            Integer type = (Integer) o;
//            switch (type.intValue()) {
//                case EasyFlexibleRecyclerViewHelper.REFRESH:
//                    simpleRecyclerView.finishRefresh(true);
//                    break;
//                case EasyFlexibleRecyclerViewHelper.LOADMORE:
//                    simpleRecyclerView.finishLoadMore(-1);
//                    break;
//            }
//        }
//    }
//    @Inject
//    IndexFragmentPresenter presenter;
//
//    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
//        setUserVisibleHint(true);
//        setHasOptionsMenu(true);
//        getComponent(IndexFragmentComponent.class).inject(this);
//        presenter.attachView(this);
//    }
//
//    public static Fragment newInstance() {
//        Index1Fragment fragment = new Index1Fragment();
//        return fragment;
//    }
//
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (presenter != null) {
//            presenter.detachView();
//            presenter = null;
//        }
//    }
//
//
//    @Override
//    public void onSimpleListSuccess(Object o, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
//
//    }
//
//
//}
