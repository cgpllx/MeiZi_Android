package com.meizitu.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GroupImageInfo implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("images")
    private List<Image> images;
    @SerializedName("coverimage")
    private String coverimage;//封面图片
    @SerializedName("piccount")
    private int piccount;//数量
    @SerializedName("pixel")
    private String pixel;//尺寸


    public int getPiccount() {
        return piccount;
    }

    public void setPiccount(int piccount) {
        this.piccount = piccount;
    }

    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
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
}
