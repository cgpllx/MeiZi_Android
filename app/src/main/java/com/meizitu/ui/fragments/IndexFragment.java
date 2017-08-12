package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;

import com.meizitu.R;
import com.meizitu.adapter.IndexListAdapter;
import com.meizitu.core.EasyFlexibleRecyclerViewHelper;
import com.meizitu.internal.di.components.MainActivityComponent;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.mvp.presenter.IndexFragmentPresenter;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;
import com.meizitu.ui.items.Item_Index_LatestImage;
import com.meizitu.ui.items.Item_Index_NewCategory;
import com.meizitu.ui.items.Item_Index_nodata;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.EasyRecyclerView;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.ArrayUtils;


public class IndexFragment extends BaseListFragment<Item_GroupImageInfoListItem> implements IndexFragmentContract.View {
    @Inject
    IndexFragmentPresenter presenter;

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    protected void onQfangViewPrepared(View view, Bundle savedInstanceState) {
        super.onQfangViewPrepared(view, savedInstanceState);
        helper.setRefreshEnabled(false);
        gridLayoutManager.setSpanCount(6);
    }

    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        getComponent(MainActivityComponent.class).inject(this);//这里相当于会重新创建注入Presenter
        if (savedInstanceState != null) {
            //presenter.xxx(savedInstanceState);这里要恢复数据
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoListItem> onCreateEasyRecyclerAdapter() {
        return new IndexListAdapter();
    }

    @Override
    public void onCategoryListStart(Object o) {
        onSimpleListStart(o);
    }

    @Override
    public void onCategoryListError(Object o, Throwable t) {
        onSimpleListError(o, t);
    }

    @Override
    protected void refesh() {
        //super.refesh();///屏蔽父类调用请求列表，先让他请求类别
        presenter.execute();

    }

    @Override
    protected boolean noData() {
        return helper.getRecyclerAdapter().getHeaderItemCount() == 0;
    }

    @Override
    public void onCategoryListSuccess(Object o, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        deliverCategoryResult(o, responseInfo);
    }

    public void deliverCategoryResult(Object i, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        if (responseInfo != null) {
            helper.getRecyclerAdapter().addHeaderItem(new Item_Index_NewCategory());
            ArrayList list = new ArrayList<>(responseInfo.getData());
            helper.getRecyclerAdapter().addHeaderItems(list);
            execute(EasyFlexibleRecyclerViewHelper.LOADMORE);
        }
    }

    @Override
    public void deliverResult(Object i, ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>> pagingResultQfangResult) {
        if (helper.isSuccess(pagingResultQfangResult)) {
            Paging<List<Item_GroupImageInfoListItem>> pagingResult = pagingResultQfangResult.getData();
            if (pagingResult != null) {
                List<Item_GroupImageInfoListItem> listItems= pagingResult.getData();
                if(!ArrayUtils.isEmpty(listItems)){
                    helper.getRecyclerAdapter().addHeaderItem(new Item_Index_LatestImage());
                    super.deliverResult(i, pagingResultQfangResult);
                    return;//正常有数据就返回
                }
            }
        }
        List list=new ArrayList<>();
        list.add(new Item_Index_nodata());
        helper.setDatas(list);
    }

}
