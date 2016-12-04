package com.meizitu.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.meizitu.R;
import com.meizitu.internal.di.HasComponent;
import com.meizitu.internal.di.components.DaggerImageComponent;
import com.meizitu.internal.di.components.ImageComponent;
import com.meizitu.internal.di.modules.ImageModule;
import com.meizitu.ui.fragments.ImageDetailsFragment;
import com.meizitu.ui.fragments.ImageListFragment;

public class ImageDetailsActivity extends BaseActivity implements HasComponent<ImageComponent> {
    ImageComponent component;
    public static final String Imagecategory_ID = "Imagecategory_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        this.initializeInjector();
        initTitleBar();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, ImageDetailsFragment.newFragment(), "ImageListFragment").commit();

    }

    private void initializeInjector() {
        int id = getIntent().getIntExtra(Imagecategory_ID, 0);
        this.component = DaggerImageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .imageModule(new ImageModule(id))
                .activityModule(getActivityModule())
                .build();

    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, ImageDetailsActivity.class);
        intent.putExtra(Imagecategory_ID, id);
        return intent;

    }

    @Override
    public ImageComponent getComponent() {
        return component;
    }
}
