package com.meizitu.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class GroupImageInfo implements Parcelable {
    @Expose
    private int id;
    @Expose
    private String title;
    @Expose
    private List<Image> images;
    @Expose
    private String coverimage;//封面图片
    @Expose
    private int piccount;//数量
    @Expose
    private String pixel;//尺寸

    private boolean status;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

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
        dest.writeByte(status ? (byte) 1 : (byte) 0);
    }

    protected GroupImageInfo(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.images = new ArrayList<Image>();
        in.readList(this.images, List.class.getClassLoader());
        this.coverimage = in.readString();
        this.piccount = in.readInt();
        this.pixel = in.readString();
        this.status = in.readByte() != 0;
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
