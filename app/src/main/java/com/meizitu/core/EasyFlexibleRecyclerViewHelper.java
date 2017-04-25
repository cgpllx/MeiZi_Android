package com.meizitu.core;



import android.support.v7.util.DiffUtil;
import android.support.v7.util.SortedList;

import com.meizitu.pojo.Paging;
import com.meizitu.ui.views.SimpleRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.easyandroid.easycore.EAResult;
import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyrecyclerview.listener.OnEasyProgressClickListener;
import cc.easyandroid.easyrecyclerview.listener.OnLoadMoreListener;
import cc.easyandroid.easyrecyclerview.listener.OnRefreshListener;
import cc.easyandroid.easyutils.ArrayUtils;
import cc.easyandroid.easyutils.EasyToast;


public class EasyFlexibleRecyclerViewHelper<T extends IFlexible>   implements OnLoadMoreListener, OnRefreshListener, OnEasyProgressClickListener {

    private SimpleRecyclerView mEasyRecyclerView;
    private EasyFlexibleAdapter<T> mEasyRecyclerAdapter;
    public static final int REFRESH = 1;
    public static final int LOADMORE = 2;
    private int eachPageNumber = 20;
    private boolean mShouldPullLoad;
    private boolean mShouldPullRefresh;

    public EasyFlexibleRecyclerViewHelper(SimpleRecyclerView easyRecyclerView, EasyFlexibleAdapter<T> easyRecyclerAdapter, int eachPageNumber, boolean shouldPullLoad, boolean shouldPullRefresh) {
        this.mEasyRecyclerView = easyRecyclerView;
        this.mEasyRecyclerAdapter = easyRecyclerAdapter;
        this.eachPageNumber = eachPageNumber;
        this.mShouldPullLoad = shouldPullLoad;
        this.mShouldPullRefresh = shouldPullRefresh;

        mEasyRecyclerView.setRefreshEnabled(shouldPullRefresh);
        mEasyRecyclerView.setLoadMoreEnabled(shouldPullLoad);
        mEasyRecyclerView.setOnLoadMoreListener(this);
        mEasyRecyclerView.setOnRefreshListener(this);
        mEasyRecyclerView.setAdapter(mEasyRecyclerAdapter);
        mEasyRecyclerView.setOnEasyProgressClickListener(this);
        mEasyRecyclerAdapter.setItemAnimation(new AlphaInAnimation(0.3f));
    }

    public void setRefreshEnabled(boolean enabled) {
        mShouldPullRefresh = enabled;
        mEasyRecyclerView.setRefreshEnabled(enabled);
        SortedList sortedList;

    }

    public void setLoadMoreEnabled(boolean enabled) {
        this.mShouldPullLoad = enabled;
        mEasyRecyclerView.setLoadMoreEnabled(enabled);

    }

    public EasyFlexibleRecyclerViewHelper(SimpleRecyclerView easyRecyclerView, EasyFlexibleAdapter<T> easyRecyclerAdapter) {
        this(easyRecyclerView, easyRecyclerAdapter, 20, true, true);
    }


    public void setDatas(final Paging<List<T>> pagingResult) {

        final List<T> datas = pagingResult.getData();
        int pageIndex = pagingResult.getCurrentPageNo();
        int pageCount = pagingResult.getTotalPage();
        pageIndex = mEasyRecyclerView.getCurrentPage() + 1;
        try {
            if (mEasyRecyclerView.isRefreshIng()) {// 刷新 成功
                if (!ArrayUtils.isEmpty(datas)) {// 有数据
                    if (mShouldPullLoad && pageIndex <= pageCount) {

                        mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_COMPLETED);
                    } else {
                        mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FULLCOMPLETED);
                    }

                    mEasyRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {

                            mEasyRecyclerAdapter.setItems(datas);
                            mEasyRecyclerView.flipPage();
                        }
                    });
                    // KToast.showShort(context, "刷新完成");
                } else {// 没有数据
//                    mEasyRecyclerAdapter.
                    mEasyRecyclerAdapter.clearItems();// 移除之前的
                    // listview.showErrorView();
//                    mListview.setPullLoadEnable(false);
                    throw new NoneDataException();
                }
            } else {// 加载成功
                if (!ArrayUtils.isEmpty(datas)) {// 有数据
                    mEasyRecyclerAdapter.addItems(datas);
                    // KToast.showShort(context, "加载完成");
                    mEasyRecyclerView.flipPage();
                    if (mShouldPullLoad && pageIndex < pageCount) {
                        mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_COMPLETED);
                    } else {
                        mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FULLCOMPLETED);
                    }
                } else {// 没有数据
                    mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FULLCOMPLETED);
                    throw new NoneDataException();
                }
            }
        } catch (NoneDataException e) {
            System.out.println("mListview.showEmptyView();");
            mEasyRecyclerView.showEmptyView();
        } finally {
//            mListview.stopPull();//
            mEasyRecyclerView.finishLoadMore(-1);
            mEasyRecyclerView.finishRefresh(true);
        }
    }

    public void xxx(){

//        List<T> old_students = mEasyRecyclerAdapter.getItems();
//                DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MyCallback(this,), true);
//        mEasyRecyclerAdapter.setItems(students);
//                 result.dispatchUpdatesTo(mEasyRecyclerAdapter);
    }


    public int getOldListSize() {
        return mEasyRecyclerAdapter.getItemCount();
    }




    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }


    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    class MyCallback extends DiffUtil.Callback{
        List<T> newData;
        EasyFlexibleRecyclerViewHelper helper;

        public MyCallback(EasyFlexibleRecyclerViewHelper helper,List<T> newData) {
            this.helper = helper;
            this.newData = newData;
        }

        @Override
        public int getOldListSize() {
            return helper.getOldListSize();
        }

        @Override
        public int getNewListSize() {
            return newData.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            System.out.println("areItemsTheSame:oldItemPosition="+oldItemPosition+"   newItemPosition="+newItemPosition);
            return false;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            System.out.println("areContentsTheSame:oldItemPosition="+oldItemPosition+"   newItemPosition="+newItemPosition);
            return false;
        }
    }

    public void setDatas(final List<T> datas) {
        try {
            if (mEasyRecyclerView.isRefreshIng()) {// 刷新 成功
                if (!ArrayUtils.isEmpty(datas)) {// 有数据
                    if (mShouldPullLoad && datas.size() >= eachPageNumber) {
                        mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_COMPLETED);
                    } else {
                        mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FULLCOMPLETED);
                    }

                    mEasyRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mEasyRecyclerAdapter.setItems(datas);
                            mEasyRecyclerView.flipPage();
                        }
                    });
                    // KToast.showShort(context, "刷新完成");
                } else {// 没有数据
//                    mEasyRecyclerAdapter.
                    mEasyRecyclerAdapter.clearItems();// 移除之前的
                    // listview.showErrorView();
//                    mListview.setPullLoadEnable(false);
                    throw new NoneDataException();
                }
            } else {// 加载成功
                if (!ArrayUtils.isEmpty(datas)) {// 有数据
                    mEasyRecyclerAdapter.addItems(datas);
                    // KToast.showShort(context, "加载完成");
                    mEasyRecyclerView.flipPage();
                    if (mShouldPullLoad && datas.size() >= eachPageNumber) {
                        mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_COMPLETED);
                    } else {
                        mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FULLCOMPLETED);
                    }
                } else {// 没有数据
                    mEasyRecyclerView.finishLoadMore(EasyRecyclerView.FooterHander.LOADSTATUS_FULLCOMPLETED);
                    throw new NoneDataException();
                }
            }
        } catch (NoneDataException e) {
            System.out.println("mListview.showEmptyView();");
            mEasyRecyclerView.showEmptyView();
        } finally {
//            mListview.stopPull();//
            mEasyRecyclerView.finishLoadMore(-1);
            mEasyRecyclerView.finishRefresh(true);
        }
    }

    public void setData(T data) {
        List<T> datas = new ArrayList<>();
        if (data != null) {
            datas.add(data);
        }
        setDatas(datas);
    }

    public void clearAllData() {
        mEasyRecyclerAdapter.clearItems();
        mEasyRecyclerView.showEmptyView();
    }

    public boolean isSuccess(EAResult jsonResult) {
        // listview.stopPull();//这里不能放在这里
        if (jsonResult != null && jsonResult.isSuccess()) {
            return true;
        } else {
            mEasyRecyclerView.showErrorView();
            EasyToast.showShort(mEasyRecyclerView.getContext().getApplicationContext(), "服务器或网络异常");
            mEasyRecyclerView.finishLoadMore(-1);
            mEasyRecyclerView.finishRefresh(true);
            return false;
        }
    }

    public EasyFlexibleAdapter<T> getRecyclerAdapter() {
        return mEasyRecyclerAdapter;
    }

    @Override
    public void onLoadMore(EasyRecyclerView.FooterHander footerHander) {

    }

    public int getCurrentPage() {
        return mEasyRecyclerView.getCurrentPage();
    }

    @Override
    public void onRefresh() {
        mEasyRecyclerView.resetPage();
    }

    @Override
    public void onLoadingViewClick() {

    }

    @Override
    public void onEmptyViewClick() {
        mEasyRecyclerView.autoRefresh();
    }

    @Override
    public void onErrorViewClick() {
        mEasyRecyclerView.autoRefresh();
    }
}
