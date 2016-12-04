package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.meizitu.GroupImageInfoListAdapter;
import com.meizitu.ImageApplication;
import com.meizitu.It.IToggle;
import com.meizitu.R;
import com.meizitu.internal.di.components.ImageComponent;
import com.meizitu.items.Item_GroupImageInfoListItem;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.EasyHttpRequestParaWrap;

import java.util.List;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyutils.EasyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageListFragment extends QfangFlexibleListFragment<Item_GroupImageInfoListItem> implements IToggle {

    public static final String ID = "id";

    public class RequestTag {
        public static final String OPENALL = "OPENALL";
        public static final String CLOSEALL = "CLOSEALL";
        public static final String OPENSINGLE = "OPENSINGLE";
        public static final String CLOSESINGLE = "CLOSESINGLE";
        private String type;
        private Item_GroupImageInfoListItem item_groupImageInfoListItem;

        public Item_GroupImageInfoListItem getItem_groupImageInfoListItem() {
            return item_groupImageInfoListItem;
        }

        public void setItem_groupImageInfoListItem(Item_GroupImageInfoListItem item_groupImageInfoListItem) {
            this.item_groupImageInfoListItem = item_groupImageInfoListItem;
        }

        public RequestTag(String type, Item_GroupImageInfoListItem item_groupImageInfoListItem) {
            this.type = type;
            this.item_groupImageInfoListItem = item_groupImageInfoListItem;
        }

        public RequestTag(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    protected QfangEasyWorkPresenter<ResponseInfo> presenter = new QfangEasyWorkPresenter<>();//使用clean


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
//        DaggerBaseFragmentComponent.builder().appComponent(ImageApplication.get(getContext()).getAppComponent()).build().inject(this);
        getComponent(ImageComponent.class).inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(new EasyWorkContract.View<ResponseInfo>() {
            @Override
            public void onStart(Object o) {
                switch (o.toString()) {
                    case RequestTag.CLOSEALL:
                        EasyToast.showShort(getContext(), "CLOSEALLonStart");
                        break;
                    case RequestTag.OPENALL:
                        EasyToast.showShort(getContext(), "OPENALLonStart");
                        break;
                    case RequestTag.CLOSESINGLE:
                        EasyToast.showShort(getContext(), "CLOSESINGLEonStart");
                        RequestTag requestTag = (RequestTag) o;
                        Item_GroupImageInfoListItem item_groupImageInfoListItem = requestTag.getItem_groupImageInfoListItem();
                        item_groupImageInfoListItem.setStatus(false);
                        helper.getRecyclerAdapter().updateItem(item_groupImageInfoListItem, null);
                        break;
                    case RequestTag.OPENSINGLE:
                        EasyToast.showShort(getContext(), "OPENSINGLEonStart");
                        requestTag = (RequestTag) o;
                        item_groupImageInfoListItem = requestTag.getItem_groupImageInfoListItem();
                        item_groupImageInfoListItem.setStatus(true);
                        helper.getRecyclerAdapter().updateItem(item_groupImageInfoListItem, null);
                        break;
                }
            }

            @Override
            public void onError(Object o, Throwable throwable) {
                EasyToast.showShort(getContext(), "error=" + throwable != null ? throwable.getMessage() : "");
                hideLoading();
            }

            @Override
            public void onSuccess(Object o, ResponseInfo responseInfo) {
                switch (o.toString()) {
                    case RequestTag.CLOSEALL:
                        EasyToast.showShort(getContext(), "CLOSEALLonSuccess");
                        autoRefresh();
                        break;
                    case RequestTag.OPENALL:
                        EasyToast.showShort(getContext(), "OPENALLonSuccess");
                        autoRefresh();
                        break;
                    case RequestTag.CLOSESINGLE:
                        EasyToast.showShort(getContext(), "CLOSESINGLEonSuccess");
                        break;
                    case RequestTag.OPENSINGLE:
                        EasyToast.showShort(getContext(), "OPENSINGLEonSuccess");
                        break;
                }
                hideLoading();
            }
        });
    }

    public static ImageListFragment newInstance() {
        ImageListFragment fragment = new ImageListFragment();
        fragment.setUserVisibleHint(true);
        return fragment;
    }

    @Override
    protected EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> onCreateRequestValues(int pulltype, Bundle paraBundle) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.adminGroupImageInfoList(paraBundle.getInt(ID), helper.getCurrentPage() + 1));
        return new EasyWorkUseCase.RequestValues<>(pulltype, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoListItem> onCreateEasyRecyclerAdapter() {
        return new GroupImageInfoListAdapter(getContext(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.imagelist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close:
                closeAll();
                break;
            case R.id.open:
                openAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeAll() {
        Bundle paraBundle = EasyHttpRequestParaWrap.getHttpParaFromFragment(this);
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.closeGroupImageInfoByCategoryCode(paraBundle.getInt(ID)));
        presenter.execute(new EasyWorkUseCase.RequestValues<>(new RequestTag(RequestTag.CLOSEALL), easyCall, null));
    }

    private void openAll() {
        Bundle paraBundle = EasyHttpRequestParaWrap.getHttpParaFromFragment(this);
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.openGroupImageInfoByCategoryCode(paraBundle.getInt(ID)));
        presenter.execute(new EasyWorkUseCase.RequestValues<>(new RequestTag(RequestTag.OPENALL), easyCall, null));
    }
    @Override
    public void closeSingle(Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.closeGroupImageInfoById(item_groupImageInfoListItem.getId()));
        presenter.execute(new EasyWorkUseCase.RequestValues<>(new RequestTag(RequestTag.CLOSESINGLE, item_groupImageInfoListItem), easyCall, null));
    }
    @Override
    public void openSingle(Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.openGroupImageInfoById(item_groupImageInfoListItem.getId()));
        presenter.execute(new EasyWorkUseCase.RequestValues<>(new RequestTag(RequestTag.OPENSINGLE, item_groupImageInfoListItem), easyCall, null));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
