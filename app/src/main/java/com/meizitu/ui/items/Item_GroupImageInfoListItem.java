package com.meizitu.ui.items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.meizitu.core.IToggle;
import com.meizitu.R;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.ui.activitys.ImageDetailsActivity;
import com.meizitu.utils.ImageUtils;

import java.util.List;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyrecyclerview.holders.FlexibleViewHolder;
import cc.easyandroid.easyrecyclerview.items.IFlexible;
import cc.easyandroid.easyui.utils.EasyViewUtil;
import cc.easyandroid.easyutils.DisplayUtils;


@SuppressLint("ParcelCreator")
public class Item_GroupImageInfoListItem extends GroupImageInfo implements IFlexible<Item_GroupImageInfoListItem.ViewHolder> {

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
        return R.layout.item_groupimageinfolist;
    }

    @Override
    public Item_GroupImageInfoListItem.ViewHolder createViewHolder(EasyFlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(EasyFlexibleAdapter adapter, Item_GroupImageInfoListItem.ViewHolder holder, int position, List payloads) {
        holder.setData(this);
    }


    public class ViewHolder extends FlexibleViewHolder {
        ImageView image;
        TextView title;
        TextView imagecount;

        GroupImageInfo imageInfo;
        Switch switchText;
        IToggle iToggle;

        public ViewHolder(final View view, EasyFlexibleAdapter adapter) {
            super(view, adapter);
            iToggle = (IToggle) adapter;
            image = EasyViewUtil.findViewById(view, R.id.image);
            title = EasyViewUtil.findViewById(view, R.id.title);
            imagecount = EasyViewUtil.findViewById(view, R.id.imagecount);
            switchText = EasyViewUtil.findViewById(view, R.id.switchText);
//            switchText.toggle();
//            switchText.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    iToggle.
//                }
//            });

        }

        //-------------------
        public void setData(final Item_GroupImageInfoListItem imageInfo) {
            this.imageInfo = imageInfo;
            ImageUtils.clear(image);
            ImageUtils.load(getContext(), image, imageInfo.getCoverimage(), R.drawable.imagebackground);
            title.setText(imageInfo.getTitle());
            imagecount.setText(imageInfo.getPiccount() + "pics");
            //int widthPixels = WindowUtil.getDisplayMetrics(getContext()).widthPixels - DisplayUtils.dp2Px(getContext(), 10);
            switchText.setChecked(imageInfo.isStatus());
//            try {
//                String pixelString = imageInfo.getPixel();
//                if (!TextUtils.isEmpty(pixelString)) {
//                    String[] pixelStringArray = pixelString.split("\\*");
//                    if (pixelStringArray.length == 2) {
//                        String pixel_x = pixelStringArray[0];
//                        String pixel_y = pixelStringArray[1];
//                        int x = Integer.parseInt(pixel_x);
//                        int y = Integer.parseInt(pixel_y);
//                        double ratio = widthPixels * 1.0 / (x);
//                        int height = (int) (y * ratio);
//                        ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
//                        layoutParams.height = height;
//                        image.setLayoutParams(layoutParams);
//                    } else {
//                        setImageDefaultLayoutParams();
//                    }
//                } else {
//                    setImageDefaultLayoutParams();
//                }
//            } catch (Exception e) {
            setImageDefaultLayoutParams();
//            }
            switchText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageInfo.isStatus()) {
                        iToggle.closeSingle(imageInfo);
                    }else{
                        iToggle.openSingle(imageInfo);
                    }
                }
            });
        }

        void setImageDefaultLayoutParams() {
            ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
            layoutParams.height = DisplayUtils.dp2Px(getContext(), 300);
            image.setLayoutParams(layoutParams);
        }

        //-------------------
        public Context getContext() {
            return getContentView().getContext();
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            if (imageInfo != null) {
                Intent intent = ImageDetailsActivity.newIntent(getContext(), imageInfo.getId());
                getContext().startActivity(intent);
            }
        }
    }
}