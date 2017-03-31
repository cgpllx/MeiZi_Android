package com.meizitu.ui.activitys;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerImageListComponent;
import com.meizitu.internal.di.components.ImageListComponent;
import com.meizitu.internal.di.modules.ImageListModule;
import com.meizitu.pojo.Category;
import com.meizitu.ui.fragments.ImageListFragment;


public class ImageListActivity extends BaseActivity implements HasComponent<ImageListComponent> {

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
        initializeInjector(gategoryId);
        setTitle(category.getCategory_name());

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(IMAGELISTTAG);
        if (fragment == null) {
            replaceFragment(R.id.fragmentContainer, ImageListFragment.newInstance(), IMAGELISTTAG);
        }

//        Locale.getDefault().getLanguage()
//        System.out.println("Locale.getDefault().getLanguage()="+ Locale.getDefault().getLanguage());
//        new Locale("zh").getLanguage().equals(Locale.getDefault().getLanguage()))
//        getResources().getConfiguration().getLocales()
    }

    private void initializeInjector(int gategoryId) {
        this.component = DaggerImageListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .imageListModule(new ImageListModule(gategoryId))
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
