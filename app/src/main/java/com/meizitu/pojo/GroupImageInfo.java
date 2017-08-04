package com.meizitu.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import cc.easyandroid.easydb.core.EasyDbObject;

public class GroupImageInfo implements Parcelable, EasyDbObject {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("images")
    private ArrayList<Image> images;
    @SerializedName("coverimage")
    private String coverimage;//封面图片
    @SerializedName("piccount")
    private int piccount;//数量
    @SerializedName("pixel")
    private String pixel;//尺寸
    @SerializedName("localcoverimage")
    private String localcoverimage;

    public String getLocalcoverimage() {
        return localcoverimage;
    }

    public int getPiccount() {
        return piccount;
    }


    public String getPixel() {
        return pixel;
    }


    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public ArrayList<Image> getImages() {
        return images;
    }

    public String getCoverimage() {
        return coverimage;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        GroupImageInfo groupImageInfo = (GroupImageInfo) obj;
        if (id != groupImageInfo.id) {
            return false;
        }
        return true;
    }

    public GroupImageInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeList(this.images);
        dest.writeString(this.coverimage);
        dest.writeInt(this.piccount);
        dest.writeString(this.pixel);
    }

    protected GroupImageInfo(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.images = new ArrayList<Image>();
        in.readList(this.images, List.class.getClassLoader());
        this.coverimage = in.readString();
        this.piccount = in.readInt();
        this.pixel = in.readString();
    }

    public static final Creator<GroupImageInfo> CREATOR = new Creator<GroupImageInfo>() {
        public GroupImageInfo createFromParcel(Parcel source) {
            return new GroupImageInfo(source);
        }

        public GroupImageInfo[] newArray(int size) {
            return new GroupImageInfo[size];
        }
    };

    @Override
    public String buildKeyColumn() {
        return id + "";
    }
}
