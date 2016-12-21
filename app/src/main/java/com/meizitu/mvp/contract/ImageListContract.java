package com.meizitu.mvp.contract;

import com.meizitu.pojo.Paging;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;
import com.meizitu.mvp.presenter.SimpleWorkPresenter;
import com.meizitu.pojo.ResponseInfo;

import java.util.List;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * 屋苑menu的Contract
 */
public interface ImageListContract {
    interface View extends IEasyView {

    }

    abstract class Presenter extends SimpleWorkPresenter<View> {

       public abstract EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> getRequestValues(int pulltype, int pageIndex);

    }
}
