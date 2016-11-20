package com.meizitu.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.meizitu.R;
import com.meizitu.ui.fragments.ImageDetailsFragment;
import com.meizitu.ui.fragments.ImageListFragment;

public class ImageDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        initTitleBar();
        int id= getIntent().getIntExtra(ImageDetailsFragment.Imagecategory_ID,0);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, ImageDetailsFragment.newFragment(id),"ImageListFragment").commit();

    }

    public static Intent newIntent(Context context,int id) {
        Intent intent = new Intent(context, ImageDetailsActivity.class);
        intent.putExtra(ImageDetailsFragment.Imagecategory_ID,id);
        return intent;

    }

}
