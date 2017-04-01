package com.meizitu.mvp.presenter;


import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.meizitu.R;
import com.meizitu.core.ImageDB;
import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.ImageDetailsContract;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;
import com.meizitu.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easymvp.PresenterLoader;
import cc.easyandroid.easymvp.call.EasyThreadCall;
import cc.easyandroid.easyutils.EasyToast;

@PerActivity
public class ImageDetailsPresenter extends SimpleWorkPresenter<ImageDetailsContract.View> implements ImageDetailsContract.Presenter {


    final ImageApi imageApi;
    final GroupImageInfo groupImageInfo;
    final Context context;

    @Inject
    public ImageDetailsPresenter(Context context, ImageApi imageApi, GroupImageInfo groupImageInfo) {
        this.context = context;
        this.imageApi = imageApi;
        this.groupImageInfo = groupImageInfo;
    }

    private void exeDownloadRequest(String imageurl, final Activity activity) {
        final FutureTarget<File> future = Glide.with(context).load(imageurl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        EasyWorkUseCase.RequestValues<File> requestValues = new EasyWorkUseCase.RequestValues<>("", new EasyThreadCall<>(new PresenterLoader<File>() {
            @Override
            public File loadInBackground() throws Exception {
                return down(future);
            }
        }), "");
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<File>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<File> responseValue) {
                onDownloadSuccess(responseValue.getData(), activity);
            }

            @Override
            public void onError(Throwable throwable) {
                onDownloadError(throwable, activity);
            }
        });
    }

    private File down(FutureTarget<File> future) throws ExecutionException, InterruptedException, IOException {
        File cacheFile = future.get();
        if (cacheFile != null) {
            File targetFile = new File(FileUtil.getSdpath() + File.separator + "mm", cacheFile.getName() + ".jpg");
            if (!targetFile.exists()) {
                File parentFile = targetFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                targetFile.createNewFile();
            }
            FileUtil.copyFileToTargetFile(cacheFile, targetFile);
            return targetFile;
        }
        return null;
    }

    private void exeShare(String imageurl, final Activity activity) {//分享和下载是一样额，都必须先下载
        final FutureTarget<File> future = Glide.with(context).load(imageurl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        EasyWorkUseCase.RequestValues<File> requestValues = new EasyWorkUseCase.RequestValues<>("", new EasyThreadCall<>(new PresenterLoader<File>() {
            @Override
            public File loadInBackground() throws Exception {
                return down(future);
            }
        }), "");
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<File>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<File> responseValue) {
                onShare(responseValue.getData(), activity);
            }

            @Override
            public void onError(Throwable throwable) {
                onShareError(throwable, activity);
            }
        });
    }

    public void onDownloadError(Throwable throwable, Activity activity) {
        throwable.printStackTrace();
        EasyToast.showLong(activity, activity.getString(R.string.downloadError));
    }

    public void onShareError(Throwable var2, Activity activity) {
        var2.printStackTrace();
        EasyToast.showLong(activity, activity.getString(R.string.shareError));
    }


    private void onShare(File imageFile, Activity activity) {
        ShareCompat.IntentBuilder.from(activity)
                .setType("image/*")//
                .setStream(Uri.fromFile(imageFile))
                .setChooserTitle(activity.getString(R.string.app_name))
                .startChooser();
    }

    private void onDownloadSuccess(File imageFile, Activity activity) {
        if (imageFile != null) {
            MediaScannerConnection.scanFile(activity, new String[]{imageFile.getAbsolutePath()}, null, null);
            EasyToast.showShort(activity, activity.getString(R.string.saveAddress, imageFile.getAbsolutePath()));
        }
    }


    @Override
    public void execute() {
        EasyCall<ResponseInfo<GroupImageInfo>> easyCall = new RetrofitCallToEasyCall<>(imageApi.queryGroupImageInfoDetails(groupImageInfo.getId()));
        EasyWorkUseCase.RequestValues<ResponseInfo<GroupImageInfo>> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        if (isViewAttached()) {
            getView().onStart(requestValues.getTag());
        }
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo<GroupImageInfo>>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo<GroupImageInfo>> responseValue) {
                ResponseInfo<GroupImageInfo> responseInfo = responseValue.getData();
                if (isViewAttached()) {
                    getView().onSuccess(responseInfo);
                }

                System.out.println();
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onError(throwable);
                }
            }
        });
    }

    @Override
    public void handleNavigationItemSelected(MenuItem item, Activity activity, String imageurl) {
        switch (item.getItemId()) {
            case R.id.detailmenu_down:
                exeDownloadRequest(imageurl, activity);
                break;
            case R.id.detailmenu_share:
                exeShare(imageurl, activity);
                break;
            case R.id.detailmenu_favorites:
//                if (item.getActionView().isSelected()) {
//                    item.getActionView().setSelected(true);
//                } else {
//                    item.getActionView().setSelected(false);
//                }
                try {
                    ImageDB.getInstance(activity).getDao(ImageDB.TABNAME_GROUPIMAGEINFO).insert(groupImageInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
