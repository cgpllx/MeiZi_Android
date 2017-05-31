package com.meizitu.core;


import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
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


public class EasyFlexibleRecyclerViewHelper<T extends IFlexible> implements OnLoadMoreListener, OnRefreshListener, OnEasyProgressClickListener {

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
//                            mEasyRecyclerAdapter.setItems(datas);
                            onNewDataArrived(datas);
                            mEasyRecyclerView.flipPage();
                        }
                    });
                } else {// 没有数据
                    mEasyRecyclerAdapter.clearItems();// 移除之前的
                    throw new NoneDataException();
                }
            } else {// 加载成功
                if (!ArrayUtils.isEmpty(datas)) {// 有数据
                    mEasyRecyclerAdapter.addItems(datas);
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
            mEasyRecyclerView.showEmptyView();
        } finally {
            mEasyRecyclerView.finishLoadMore(-1);
            mEasyRecyclerView.finishRefresh(true);
        }
    }

    class MyCallback extends DiffUtil.Callback {
        List<T> newData;
        List<T> oldData;

        public MyCallback(List<T> newData, List<T> oldData) {
            this.newData = newData;
            this.oldData = oldData;
        }

        @Override
        public int getOldListSize() {
            return oldData.size() + 1 + 1;
        }

        @Override
        public int getNewListSize() {
            return newData.size() + 1 + 1;
        }

        // 两个Item是不是同一个东西，
        // 它们的内容或许不一样，但id相同代表就是同一个
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldItemPosition == 0 && newItemPosition == 0) {
                return true;
            } else if (oldItemPosition == 0) {
                return false;
            } else if (newItemPosition == 0) {
                return false;
            }//header
            if (oldItemPosition == getOldListSize()-1 && newItemPosition == getNewListSize()-1) {
                return true;
            } else if (oldItemPosition == getOldListSize() - 1 || newItemPosition == getNewListSize() - 1) {
                return false;
            }
            T oldItem = oldData.get(oldItemPosition - 1);
            T newItem = newData.get(newItemPosition - 1);
            System.out.println("areItemsTheSame:oldItemPosition=" + oldItemPosition + "   newItemPosition=" + newItemPosition + "---" + (oldItem.getLayoutRes() == newItem.getLayoutRes()));
//            return oldItem.getLayoutRes() == newItem.getLayoutRes();
            return oldItem.equals(newItem);
        }

        // 比较两个Item的内容是否一致，如不一致则会调用adapter的notifyItemChanged()
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldItemPosition == 0 && newItemPosition == 0) {
                return true;
            } else if (oldItemPosition == 0 || newItemPosition == 0) {
                return false;
            }  //header
            if (oldItemPosition == getOldListSize() - 1 && newItemPosition == getNewListSize() - 1) {
                return true;
            } else if (oldItemPosition == getOldListSize() - 1 || newItemPosition == getNewListSize() - 1) {
                return false;
            }
            T oldItem = oldData.get(oldItemPosition - 1);
            T newItem = newData.get(newItemPosition - 1);
            System.out.println("areContentsTheSame:oldItemPosition=" + oldItemPosition + "   newItemPosition=" + newItemPosition + "---" + oldItem.equals(newItem));
            return oldItem.equals(newItem);
        }
    }

    void onNewDataArrived(List<T> newItems) {
        //mEasyRecyclerAdapter.setItemsAndNotifyChanged(newItems);
        List<T> oldItems = mEasyRecyclerAdapter.getItems();
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MyCallback(newItems, oldItems), false);
        mEasyRecyclerAdapter.setItems(newItems);
//        result.dispatchUpdatesTo(mEasyRecyclerAdapter);
        result.dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                System.out.println("cgp onInserted " + "position =" + position + " count=" + count);
                mEasyRecyclerAdapter.notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                System.out.println("cgp onRemoved " + "position =" + position + " count=" + count);
                mEasyRecyclerAdapter.notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                System.out.println("cgp onMoved " + "fromPosition =" + fromPosition + " toPosition=" + toPosition);
                mEasyRecyclerAdapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                System.out.println("cgp onChanged " + "position =" + position + "count =" + count + " payload=" + payload);
                mEasyRecyclerAdapter.notifyItemRangeChanged(position, count, payload);
            }
        });
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
