package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.meizitu.R;
import com.meizitu.adapter.SimpleFragmentPagerAdapter;
import com.meizitu.internal.di.components.ImageComponent;
import com.meizitu.mvp.contract.TabContract;
import com.meizitu.mvp.presenter.TabPresenter;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.ResponseInfo;


import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.core.progress.EasyProgressLinearLayout;
import cc.easyandroid.easyui.pojo.EasyTab;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.ArrayUtils;


public class TabFragment extends QfangBaseFragment implements TabContract.View {
    private TabLayout tablayout;

    private ViewPager viewpager;

    SimpleFragmentPagerAdapter adapter;

    EasyProgressLinearLayout easyProgressLinearLayout;

    @Inject
    TabPresenter presenter;

    public TabFragment() {
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_tab;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inject();
        tablayout = EasyViewUtil.findViewById(view, R.id.tablayout);
        viewpager = EasyViewUtil.findViewById(view, R.id.viewpager);
        easyProgressLinearLayout = EasyViewUtil.findViewById(view, R.id.easyProgressLinearLayout);
        adapter = new SimpleFragmentPagerAdapter(getFragmentManager(), getContext());
        presenter.attachView(this);
        presenter.execute();
    }

    private void inject() {
        this.getComponent(ImageComponent.class).inject(this);
    }

    @Override
    public void onTabLoadStart(Object o) {
        easyProgressLinearLayout.showLoadingView();
    }

    @Override
    public void onTabLoadSuccess(Object o, ResponseInfo<List<Category>> pagingResponseInfo) {
        if (pagingResponseInfo != null && pagingResponseInfo.isSuccess()) {
            List<Category> categories = pagingResponseInfo.getData();
            if (!ArrayUtils.isEmpty(categories)) {
                Bundle arge;
                for (Category category : categories) {
                    arge = new Bundle();
                    arge.putInt(ImageListFragment.ID, category.getCategory_code());
                    adapter.addTab(new EasyTab(category.getCategory_name(), null, ImageListFragment.class, arge));
                }
                viewpager.setAdapter(adapter);
                viewpager.setOffscreenPageLimit(adapter.getCount());
                //设置tab可以滚动
                tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                tablayout.setupWithViewPager(viewpager);
                easyProgressLinearLayout.showContentView();
            } else {
                easyProgressLinearLayout.showEmptyView();
            }
        } else {
            easyProgressLinearLayout.showEmptyView();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }


    @Override
    public void onTabLoadError(Object o, Throwable t) {
        onError(o, t);
    }


}
