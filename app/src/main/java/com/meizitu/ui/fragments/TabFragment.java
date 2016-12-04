package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizitu.R;
import com.meizitu.adapter.SimpleFragmentPagerAdapter;
import com.meizitu.internal.di.components.ImageComponent;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.EasyHttpRequestParaWrap;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyrecyclerview.core.progress.EasyProgressLinearLayout;
import cc.easyandroid.easyui.pojo.EasyTab;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.ArrayUtils;


public class TabFragment extends QfangBaseFragment implements EasyWorkContract.View<ResponseInfo<List<Category>>> {
    private TabLayout tablayout;

    private ViewPager viewpager;

    SimpleFragmentPagerAdapter adapter;

    EasyProgressLinearLayout easyProgressLinearLayout;

    @Inject
    QfangEasyWorkPresenter<ResponseInfo<List<Category>>> presenter;
    @Inject
    @Named("CategoryList")
    EasyWorkUseCase.RequestValues requestValues;

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
        presenter.execute(requestValues);
    }

    private void inject() {
        this.getComponent(ImageComponent.class).inject(this);
    }

    @Override
    public void onStart(Object o) {
        easyProgressLinearLayout.showLoadingView();
    }

    @Override
    public void onSuccess(Object o, ResponseInfo<List<Category>> pagingResponseInfo) {
        if (pagingResponseInfo != null && pagingResponseInfo.isSuccess()) {
            List<Category> categories = pagingResponseInfo.getData();
            if (!ArrayUtils.isEmpty(categories)) {
                Bundle arge;
                for (Category category : categories) {
                    arge = new Bundle();
                    Bundle paraBundle = new Bundle();
                    paraBundle.putInt(ImageListFragment.ID, category.getCategory_code());
                    EasyHttpRequestParaWrap.bindingHttpPara(arge, paraBundle);
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
}
