package com.meizitu.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;

import com.meizitu.R;
import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;

@PerActivity
public class ImageListPresenter extends AbsSimpleListPresenter<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>, ImageListContract.View> implements ImageListContract.Presenter {

    final int gategoryId;
    final ImageApi imageApi;

    @Inject
    public ImageListPresenter(ImageApi imageApi, int gategoryId) {
        this.gategoryId = gategoryId;
        this.imageApi = imageApi;
    }


    @Override
    protected EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> getRequestValues(int pulltype, int pageIndex, String cachecontrol) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.queryGroupImageInfoList(gategoryId, pageIndex, cachecontrol));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>(pulltype, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }


    @Override
    public void share(Activity activity) {
        ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText("https://play.google.com/store/apps/details?id=" + activity.getPackageName())
                .setChooserTitle(activity.getString(R.string.app_name))
                .startChooser();
    }

    @Override
    public void feedback(Activity activity) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("cgpllx@gmail.com"));
        activity.startActivity(data);
    }

    public void favourablecomment(Context activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + activity.getPackageName()));
        activity.startActivity(intent);
    }
}
