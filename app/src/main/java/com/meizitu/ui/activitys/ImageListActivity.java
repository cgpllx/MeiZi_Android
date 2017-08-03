package com.meizitu.ui.activitys;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerImageListComponent;
import com.meizitu.internal.di.components.ImageListComponent;
import com.meizitu.internal.di.modules.ImageListModule;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.pojo.Category;
import com.meizitu.ui.fragments.ImageListFragment;

import java.util.Locale;


public class ImageListActivity extends BaseSwipeBackActivity implements HasComponent<ImageListComponent> {

    ImageListComponent component;
    public static final String IMAGELISTTAG = "imagelist_tag";
    public static final String CATEGORY_EXTRA = "category";

    public static Intent newIntent(Context context, Category category) {
        Intent intent = new Intent(context, ImageListActivity.class);
        intent.putExtra(CATEGORY_EXTRA, category);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagelist);
        initTitleBar();
        Category category = getIntent().getParcelableExtra(CATEGORY_EXTRA);
        int gategoryId = category.getCategory_code();

        if (new Locale("zh").getLanguage().equals(Locale.getDefault().getLanguage())) {
            setTitle(category.getCategory_name());
        } else {
            setTitle(category.getCategory_en_name());
        }
        ImageListFragment fragment = (ImageListFragment) getSupportFragmentManager().findFragmentByTag(IMAGELISTTAG);
        if (fragment == null) {
            replaceFragment(R.id.fragmentContainer, fragment = ImageListFragment.newInstance(), IMAGELISTTAG);
        }
        initializeInjector(gategoryId, fragment);

    }

    private void initializeInjector(int gategoryId, ImageListContract.View imageListView) {
        this.component = DaggerImageListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .imageListModule(new ImageListModule(gategoryId, imageListView))
                .build();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public ImageListComponent getComponent() {
        return component;
    }
}
