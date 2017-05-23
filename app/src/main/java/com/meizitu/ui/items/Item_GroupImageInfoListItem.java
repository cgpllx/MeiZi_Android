package com.meizitu.ui.items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import cc.easyandroid.easyutils.WindowUtil;


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

        Item_GroupImageInfoListItem imageInfo;

        public ViewHolder(final View view, EasyFlexibleAdapter adapter) {
            super(view, adapter);
            image = EasyViewUtil.findViewById(view, R.id.image);
            title = EasyViewUtil.findViewById(view, R.id.title);
            imagecount = EasyViewUtil.findViewById(view, R.id.imagecount);
        }

        //-------------------
        public void setData(final Item_GroupImageInfoListItem imageInfo) {
            this.imageInfo = imageInfo;
            ImageUtils.clear(image);
            imagecount.setText(imageInfo.getPiccount() + "pics");
            final int widthPixels = WindowUtil.getDisplayMetrics(getContext()).widthPixels;
            getContentView().post(new Runnable() {
                @Override
                public void run() {
                    int viewwidthPixels = getContentView().getWidth();
                    if (viewwidthPixels <= widthPixels / 2) {
                        setImageDefaultLayoutParams();
                        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        try {
                            String pixelString = imageInfo.getPixel();
                            if (!TextUtils.isEmpty(pixelString)) {
                                String[] pixelStringArray = pixelString.split("\\*");
                                if (pixelStringArray.length == 2) {
                                    String pixel_x = pixelStringArray[0];
                                    String pixel_y = pixelStringArray[1];
                                    int x = Integer.parseInt(pixel_x);
                                    int y = Integer.parseInt(pixel_y);
                                    double ratio = viewwidthPixels * 1.0 / (x);
                                    int height = (int) (y * ratio);
                                    ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
                                    layoutParams.height = height;
                                    image.setLayoutParams(layoutParams);
                                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                                } else {
                                    setImageDefaultLayoutParams();
                                }
                            } else {
                                setImageDefaultLayoutParams();
                            }
                        } catch (Exception e) {
                            setImageDefaultLayoutParams();
                        }
                    }
                }
            });
            ImageUtils.load(getContext(), image, imageInfo.getCoverimage(), R.drawable.levellist);
            image.setImageLevel(getAdapterPosition()%9);
            title.setText(imageInfo.getTitle());
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
                //todo:检测积分是否够
                Intent intent = ImageDetailsActivity.newIntent(getContext(), imageInfo);
                getContext().startActivity(intent);
                //todo:消耗积分
            }
        }
    }

    public Item_GroupImageInfoListItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected Item_GroupImageInfoListItem(Parcel in) {
        super(in);
    }

    public static final Creator<Item_GroupImageInfoListItem> CREATOR = new Creator<Item_GroupImageInfoListItem>() {
        public Item_GroupImageInfoListItem createFromParcel(Parcel source) {
            return new Item_GroupImageInfoListItem(source);
        }

        public Item_GroupImageInfoListItem[] newArray(int size) {
            return new Item_GroupImageInfoListItem[size];
        }
    };
}
