package com.meizitu.mvp.presenter;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.meizitu.R;
import com.meizitu.core.ImageDB;
import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.ImageDetailsContract;
import com.meizitu.mvp.usecase.DeleteByIdFromDbUseCase;
import com.meizitu.mvp.usecase.GetDataFromDbUseCase;
import com.meizitu.mvp.usecase.InsertDataFromDbUseCase;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;
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
import cc.easyandroid.easypermission.EasyPermission;
import cc.easyandroid.easypermission.Rationale;
import cc.easyandroid.easypermission.RationaleListener;
import cc.easyandroid.easyutils.EasyToast;

@PerActivity
public class ImageDetailsPresenter extends SimpleWorkPresenter<ImageDetailsContract.View> implements ImageDetailsContract.Presenter {


    final ImageApi mImageApi;

    final Item_GroupImageInfoListItem mGroupImageInfo;

    final Context mContext;

    final InsertDataFromDbUseCase<Item_GroupImageInfoListItem> mInsertDataFromDbUseCase;

    final DeleteByIdFromDbUseCase mDeleteByIdFromDbUseCase;

    final GetDataFromDbUseCase<Item_GroupImageInfoListItem> mGetDataFromDbUseCase;

    @Inject
    public ImageDetailsPresenter(Context mContext, ImageApi imageApi, Item_GroupImageInfoListItem mGroupImageInfo,//
                                 InsertDataFromDbUseCase<Item_GroupImageInfoListItem> insertDataFromDbUseCase,//
                                 GetDataFromDbUseCase<Item_GroupImageInfoListItem> getDataFromDbUseCase,//
                                 DeleteByIdFromDbUseCase deleteByIdFromDbUseCase,ImageDetailsContract.View view) {
        this.mContext = mContext;
        this.mImageApi = imageApi;
        this.mGroupImageInfo = mGroupImageInfo;
        this.mInsertDataFromDbUseCase = insertDataFromDbUseCase;
        this.mDeleteByIdFromDbUseCase = deleteByIdFromDbUseCase;
        this.mGetDataFromDbUseCase = getDataFromDbUseCase;
        attachView(view);
    }

    private void exeDownloadRequest(String imageurl, final Activity activity) {

        final FutureTarget<File> future = Glide.with(mContext).load(imageurl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
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
        final FutureTarget<File> future = Glide.with(mContext).load(imageurl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
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
        EasyCall<ResponseInfo<GroupImageInfo>> easyCall = new RetrofitCallToEasyCall<>(mImageApi.queryGroupImageInfoDetails(mGroupImageInfo.getId()));
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
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onError(throwable);
                }
            }
        });
    }

    public static final int DOWNPERMISSIONREQUESTCODE = 100;

    public void handleNavigationItemSelected(MenuItem item, final Activity activity, String imageurl) {
        switch (item.getItemId()) {
            case R.id.detailmenu_down:
                // 先判断是否有权限。
                if (EasyPermission.hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 有权限，直接do anything.
                    exeDownloadRequest(imageurl, activity);
                } else {
                    // 申请权限。
                    EasyPermission.with(activity)
                            .requestCode(DOWNPERMISSIONREQUESTCODE)
                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                            .rationale(new RationaleListener() {
                                @Override
                                public void showRequestPermissionRationale(int i, Rationale rationale) {
                                    EasyPermission.rationaleDialog(activity, rationale).show();
                                }
                            }).send();
                }

                break;
            case R.id.detailmenu_share:
                exeShare(imageurl, activity);
                break;
            case R.id.detailmenu_favorites:
                if (item.getActionView().isSelected()) {
                    executeDeleteFavorites(mGroupImageInfo, item.getActionView());
                } else {
                    executeAddFavorites(mGroupImageInfo, item.getActionView());
                }
                break;
        }
    }

    @Override
    public void handleDownLoadImage(Activity activity, String imageUrl) {
        exeDownloadRequest(imageUrl, activity);
    }

    @Override
    public void handleShareImage(Activity activity, String imageUrl) {
        exeShare(imageUrl, activity);
    }

    @Override
    public void handleFavorite(MenuItem item) {
        if (item.getActionView().isSelected()) {
            executeDeleteFavorites(mGroupImageInfo, item.getActionView());
        } else {
            executeAddFavorites(mGroupImageInfo, item.getActionView());
        }
    }

    @Override
    public void initFavoriteMenu(final View actionView) {
        mUseCaseHandler.execute(mGetDataFromDbUseCase, new GetDataFromDbUseCase.RequestValues(ImageDB.TABNAME_GROUPIMAGEINFO, Item_GroupImageInfoListItem.class, mGroupImageInfo.buildKeyColumn()), new UseCase.UseCaseCallback<GetDataFromDbUseCase.ResponseValue<Item_GroupImageInfoListItem>>() {
            @Override
            public void onSuccess(GetDataFromDbUseCase.ResponseValue<Item_GroupImageInfoListItem> responseValue) {
                Item_GroupImageInfoListItem item_groupImageInfoListItem = responseValue.getDatas();
                if (item_groupImageInfoListItem != null) {
                    actionView.setSelected(true);
                } else {
                    actionView.setSelected(false);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                actionView.setSelected(false);
            }
        });
    }

    private void executeAddFavorites(Item_GroupImageInfoListItem groupImageInfo, final View actionView) {
        mUseCaseHandler.execute(mInsertDataFromDbUseCase, new InsertDataFromDbUseCase.RequestValues<>(ImageDB.TABNAME_GROUPIMAGEINFO, groupImageInfo), new UseCase.UseCaseCallback<InsertDataFromDbUseCase.ResponseValue>() {
            @Override
            public void onSuccess(InsertDataFromDbUseCase.ResponseValue responseValue) {
                if (actionView != null)
                    actionView.setSelected(true);
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

    private void executeDeleteFavorites(GroupImageInfo groupImageInfo, final View actionView) {
        mUseCaseHandler.execute(mDeleteByIdFromDbUseCase, new DeleteByIdFromDbUseCase.RequestValues(ImageDB.TABNAME_GROUPIMAGEINFO, groupImageInfo.buildKeyColumn()), new UseCase.UseCaseCallback<DeleteByIdFromDbUseCase.ResponseValue>() {
            @Override
            public void onSuccess(DeleteByIdFromDbUseCase.ResponseValue responseValue) {
                if (actionView != null)
                    actionView.setSelected(false);
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }


}
