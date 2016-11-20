package com.meizitu.ui.activitys;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meizitu.R;
import com.meizitu.adapter.SimpleFragmentPagerAdapter;
import com.meizitu.items.Item_GroupImageInfoList;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.EasyHttpRequestParaWrap;
import com.meizitu.service.QfangRetrofitCallToEasyCall;
import com.meizitu.service.QfangRetrofitManager;
import com.meizitu.ui.fragments.ImageListFragment;

import java.util.List;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyrecyclerview.core.progress.EasyProgressLinearLayout;
import cc.easyandroid.easyui.pojo.EasyTab;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.ArrayUtils;
import cc.easyandroid.easyutils.EasyToast;


public class MainActivity extends BaseActivity implements EasyWorkContract.View<ResponseInfo<List<Category>>> {
    private TabLayout tablayout;

    private ViewPager viewpager;

    QfangEasyWorkPresenter<ResponseInfo<List<Category>>> presenter = new QfangEasyWorkPresenter<>();

    SimpleFragmentPagerAdapter adapter;

    EasyProgressLinearLayout easyProgressLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitleBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        presenter.attachView(this);
        tablayout = EasyViewUtil.findViewById(this, R.id.tablayout);
        viewpager = EasyViewUtil.findViewById(this, R.id.viewpager);
        easyProgressLinearLayout = EasyViewUtil.findViewById(this, R.id.easyProgressLinearLayout);

        adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);

        EasyCall easyCall = new QfangRetrofitCallToEasyCall(QfangRetrofitManager.getApi().categoryList(30));
        presenter.execute(new EasyWorkUseCase.RequestValues("", easyCall, null));
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
                easyProgressLinearLayout.showEmptyView();
            } else {
                easyProgressLinearLayout.showEmptyView();
            }
        } else {
            easyProgressLinearLayout.showEmptyView();
        }
    }

    @Override
    public void onError(Object i, Throwable throwable) {
        super.onError(i, throwable);
        easyProgressLinearLayout.showErrorView();
    }

    private long startTime = 0;

    @Override
    public void onBackPressed() {
        long endTime = System.currentTimeMillis();
        if (startTime > (endTime - 2000)) {
            super.onBackPressed();
        } else {
            startTime = endTime;
            EasyToast.showShort(getApplicationContext(), "再按一次退出");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
