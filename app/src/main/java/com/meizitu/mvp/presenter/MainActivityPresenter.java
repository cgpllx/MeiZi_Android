package com.meizitu.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;

import com.meizitu.R;
import com.meizitu.mvp.contract.MainActivityContract;

import cc.easyandroid.easyutils.EasyToast;

public class MainActivityPresenter extends SimpleWorkPresenter<MainActivityContract.View> implements MainActivityContract.Presenter {

    public MainActivityPresenter() {

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
    public void favourableComment(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + activity.getPackageName()));
        activity.startActivity(intent);
    }
}
