package com.meizitu.adapter;

import com.meizitu.ui.items.Item_GroupImageInfoList_AD;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;

/**
 * Created by chenguoping on 16/10/26.
 */
public class GroupImageInfoListAdapter extends EasyFlexibleAdapter {
    @Override
    public boolean addItems(List items) {
//        if (!ArrayUtils.isEmpty(items) && items.size() > 17) {
//            items.add(17, new Item_GroupImageInfoList_AD());
//        }
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        items.add(new Item_GroupImageInfoList_AD());
        return super.addItems(items);
    }


}
