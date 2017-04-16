package com.meizitu.ui.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import cc.easyandroid.easyrecyclerview.EasyRecyclerView;

/**
 *
 */

public class ImageFooterHander implements EasyRecyclerView.FooterHander {
    private Context context;
    private int rotationSrc;
    private TextView footerTv;
    private ProgressBar footerBar;
    private boolean loading;
    private boolean noMore;

    public ImageFooterHander(Context context) {
        this(context, cc.easyandroid.easyrecyclerview.R.drawable.progress_small);
    }

    public ImageFooterHander(Context context, int rotationSrc) {
        this.loading = false;
        this.noMore = false;
        this.context = context;
        this.rotationSrc = rotationSrc;
    }

    public View getView() {
        View view = LayoutInflater.from(this.context).inflate(cc.easyandroid.easyrecyclerview.R.layout.default_footer, (ViewGroup) null, true);
        view.setPadding(0,0,0,150);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.footerTv = (TextView) view.findViewById(cc.easyandroid.easyrecyclerview.R.id.default_footer_title);
        this.footerTv.setText(this.context.getResources().getString(cc.easyandroid.easyrecyclerview.R.string.easyrecyclerview_click_to_loadmore));
        this.footerBar = (ProgressBar) view.findViewById(cc.easyandroid.easyrecyclerview.R.id.default_footer_progressbar);
        this.footerBar.setIndeterminateDrawable(ContextCompat.getDrawable(this.context, this.rotationSrc));
        return view;
    }

    public void showLoadCompleted() {
        this.footerTv.setText(this.context.getResources().getString(cc.easyandroid.easyrecyclerview.R.string.easyrecyclerview_click_to_loadmore));
        this.footerBar.setVisibility(View.GONE);
        this.loading = false;
        this.noMore = false;
    }

    public void showLoading() {
        this.footerTv.setText(this.context.getResources().getString(cc.easyandroid.easyrecyclerview.R.string.easyrecyclerview_loadmoreing));
        this.footerBar.setVisibility(View.VISIBLE);

        this.loading = true;
        this.noMore = false;
    }

    public void showLoadFail() {
        this.footerTv.setText(this.context.getResources().getString(cc.easyandroid.easyrecyclerview.R.string.easyrecyclerview_loadmorefail_click_to_loadmore));
        this.footerBar.setVisibility(View.GONE);
        this.loading = false;
        this.noMore = false;
    }

    public void showLoadFullCompleted() {
        this.footerTv.setText(this.context.getResources().getString(cc.easyandroid.easyrecyclerview.R.string.easyrecyclerview_loadmore_fullcompleted));
        this.footerBar.setVisibility(View.GONE);
        this.loading = false;
        this.noMore = true;
    }

    public boolean onCanLoadMore() {
        return !this.loading && !this.noMore;
    }
}