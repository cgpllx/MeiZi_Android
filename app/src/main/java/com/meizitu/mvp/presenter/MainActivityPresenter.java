package com.meizitu.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;

import com.meizitu.R;
import com.meizitu.mvp.contract.MainActivityContract;
import com.meizitu.pojo.ADInfo;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easyutils.EasyToast;

public class MainActivityPresenter extends SimpleWorkPresenter<MainActivityContract.View> implements MainActivityContract.Presenter {
    final ImageApi imageApi;

    @Inject
    public MainActivityPresenter(ImageApi imageApi) {
        this.imageApi = imageApi;
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
        try {
            Intent data = new Intent(Intent.ACTION_SENDTO);
            data.setData(Uri.parse("mailto:cgpllx@gmail.com"));
            data.putExtra(Intent.EXTRA_SUBJECT, "");
            data.putExtra(Intent.EXTRA_TEXT, "");
            activity.startActivity(data);
        } catch (Exception e) {
            EasyToast.showShort(activity, activity.getString(R.string.no_email));
        }
    }

    @Override
    public void executeAdInfoRequest() {
        EasyCall<ResponseInfo<ADInfo>> easyCall = new RetrofitCallToEasyCall<>(imageApi.queryAdInfo());
        final EasyWorkUseCase.RequestValues<ResponseInfo<ADInfo>> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo<ADInfo>>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo<ADInfo>> responseValue) {
                ResponseInfo<ADInfo> responseInfo = responseValue.getData();
                if (responseInfo != null) {
                    ADInfo adInfo = responseInfo.getData();
                    if (adInfo != null) {
                        if(isViewAttached())
                            getView().onAdInfoSuccess(adInfo);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

    @Override
    public void favourableComment(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + activity.getPackageName()));
        activity.startActivity(intent);
    }
}
