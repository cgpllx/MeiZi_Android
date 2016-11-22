package com.meizitu.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cc.easyandroid.easyui.pojo.EasyTab;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<EasyTab> easyTabs;

    private Context context;

    public void addTab(EasyTab easyTab) {
        easyTabs.add(easyTab);
        fragments.add(null);
    }

    public void delTab(int itemIndex) {
        easyTabs.remove(itemIndex);
        fragments.remove(itemIndex);//    .onDestroy();
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        easyTabs = new ArrayList<>();
    }

    List<Fragment> fragments=new ArrayList<>();

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        if (fragment == null) {
            EasyTab easyTab = easyTabs.get(position);
            fragment = Fragment.instantiate(context, easyTab.getYourFragment().getName(), easyTab.getBundle());
            fragments.add(position, fragment);
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return easyTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return easyTabs.get(position).getTabSpec();
    }
}