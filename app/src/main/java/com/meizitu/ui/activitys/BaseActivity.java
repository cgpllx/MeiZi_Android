package com.meizitu.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.meizitu.R;

import cc.easyandroid.easyutils.EasyToast;


/**
 * 全局activity父類
 */
public class BaseActivity extends AppCompatActivity {


    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    Toolbar toolbar;

    protected void initTitleBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }




    public void onError(Object i, Throwable throwable) {
//        EasyToast.showShort(getApplicationContext(), TextUtils.isEmpty(throwable.getMessage()) ? "服務器或者網絡異常" : throwable.getMessage());
        EasyToast.showShort(getApplicationContext(),   "服務器或者網絡異常"  );

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
