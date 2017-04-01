package com.meizitu.mvp.contract;

import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

/**
 * Created by chenguoping on 16/12/11.
 */
public interface FavoritesListContract {
    interface View extends SimpleListContract.View<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> {

    }

    interface Presenter extends SimpleListContract.Presenter<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>, View> {

    }
}
