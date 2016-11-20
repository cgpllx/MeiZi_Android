package com.meizitu.ui.views;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.meizitu.R;

import cc.easyandroid.easyui.utils.EasyViewUtil;

/**
 * viewpager 指示器
 */
public class ViewpagerIndicator extends LinearLayout {
    private ViewPager mViewpager;
    private TextView titleName_tv;
    private TextView pageName_tv;
    public static final String PAGE_FORMAT = "%1$d/%2$d";

    public ViewpagerIndicator(Context context) {
        super(context);
    }

    public ViewpagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.secondhandhouse_viewpager_indicator, this);
        titleName_tv = EasyViewUtil.findViewById(this, R.id.titleName);
        pageName_tv = EasyViewUtil.findViewById(this, R.id.pageName);
    }

    public void setTitleName(CharSequence titleName) {
        if (!TextUtils.isEmpty(titleName)) {
            titleName_tv.setText(titleName);
        }
    }

    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.setOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.getAdapter().registerDataSetObserver(mInternalDataSetObserver);
            mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
        }
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            PagerAdapter adapter = mViewpager.getAdapter();
            if (adapter != null) {
                int count = adapter.getCount();
                if (count > 0 && count >= position + 1) {
                    pageName_tv.setText(String.format(PAGE_FORMAT, position + 1, count));
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
        }
    };

}
