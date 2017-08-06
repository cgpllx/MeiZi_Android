package com.meizitu.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.BuildConfig;
import android.support.v4.app.ShareCompat;

import com.meizitu.R;
import com.meizitu.mvp.contract.MainActivityContract;
import com.meizitu.pojo.ADInfo;
import com.meizitu.pojo.AppInfo;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;
import com.meizitu.utils.AppUpGradeManager;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.repository.EasyWorkRepository;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easyutils.EasyToast;

public class MainActivityPresenter extends SimpleWorkPresenter<MainActivityContract.View> implements MainActivityContract.Presenter {
    final ImageApi imageApi;
    String applicationId = com.meizitu.BuildConfig.APPLICATION_ID;

    @Inject
    public MainActivityPresenter(ImageApi imageApi, MainActivityContract.View view) {
        this.imageApi = imageApi;
        attachView(view);
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
        EasyCall<ResponseInfo<ADInfo>> easyCall = new RetrofitCallToEasyCall<>(imageApi.queryAdInfo(applicationId));
        final EasyWorkUseCase.RequestValues<ResponseInfo<ADInfo>> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo<ADInfo>>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo<ADInfo>> responseValue) {
                ResponseInfo<ADInfo> responseInfo = responseValue.getData();
                if (responseInfo != null) {
                    ADInfo adInfo = responseInfo.getData();
                    if (adInfo != null) {
                        if (isViewAttached())
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
    public void executeAppInfoRequest() {
        EasyCall<ResponseInfo<AppInfo>> easyCall = new RetrofitCallToEasyCall<>(imageApi.checkAppUpdate(applicationId));
        final EasyWorkUseCase.RequestValues<ResponseInfo<AppInfo>> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        handleRequest(new EasyWorkUseCase(new EasyWorkRepository()), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo<AppInfo>>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo<AppInfo>> responseValue) {
                ResponseInfo<AppInfo> responseInfo = responseValue.getData();
                if (responseInfo != null) {
                    AppInfo adInfo = responseInfo.getData();
                    if (adInfo != null) {
                        if (isViewAttached())
                            getView().onAppInfoSuccess(adInfo);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

    @Override
    public void executeDownLoadNewApp(Context context, AppInfo appInfo) {
        long download_id = AppUpGradeManager.downLoadApk(context, appInfo);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(AppInfo.DOWNLOAD_ID, download_id).apply();
    }

    @Override
    public void favourableComment(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + activity.getPackageName()));
        activity.startActivity(intent);
    }
}
