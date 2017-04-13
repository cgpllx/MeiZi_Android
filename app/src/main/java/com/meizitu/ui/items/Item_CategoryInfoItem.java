package com.meizitu.ui.items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizitu.R;
import com.meizitu.pojo.Category;
import com.meizitu.ui.activitys.ImageListActivity;
import com.meizitu.utils.ImageUtils;

import java.util.List;
import java.util.Locale;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.holders.FlexibleViewHolder;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyui.utils.EasyViewUtil;

@SuppressLint("ParcelCreator")
public class Item_CategoryInfoItem extends Category implements IFlexible<Item_CategoryInfoItem.ViewHolder> {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean b) {

    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public void setSelectable(boolean b) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_categorylist;
    }

    @Override
    public Item_CategoryInfoItem.ViewHolder createViewHolder(EasyFlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(EasyFlexibleAdapter adapter, Item_CategoryInfoItem.ViewHolder holder, int position, List payloads) {
        holder.setData(this);
    }


    public class ViewHolder extends FlexibleViewHolder {
        ImageView image;
        TextView title;


        public ViewHolder(final View view, EasyFlexibleAdapter adapter) {
            super(view, adapter);
            image = EasyViewUtil.findViewById(view, R.id.image);
            title = EasyViewUtil.findViewById(view, R.id.title);
        }

        Category category;

        //-------------------
        public void setData(Category category) {
            this.category = category;
            if (new Locale("zh").getLanguage().equals(Locale.getDefault().getLanguage())) {
                title.setText(category.getCategory_name());
            } else {
                title.setText(category.getCategory_en_name());
            }
            System.out.println("cgp="+category.getCategory_en_name());
            ImageUtils.loadGlideCircle(getContext(), image, category.getCategory_icon(),R.mipmap.image_girl);
        }


        //-------------------
        public Context getContext() {
            return getContentView().getContext();
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            if (category != null) {
                getContext().startActivity(ImageListActivity.newIntent(getContext(), category));
            }
        }
    }


}
