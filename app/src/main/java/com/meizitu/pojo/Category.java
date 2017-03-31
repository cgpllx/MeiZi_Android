package com.meizitu.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable {

    @SerializedName("category_code")
    private int category_code;

    @SerializedName("category_name")
    private String category_name;

    @SerializedName("category_pinyin")
    private String category_pinyin;

    public int getCategory_code() {
        return category_code;
    }

    public void setCategory_code(int category_code) {
        this.category_code = category_code;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_pinyin() {
        return category_pinyin;
    }

    public void setCategory_pinyin(String category_pinyin) {
        this.category_pinyin = category_pinyin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.category_code);
        dest.writeString(this.category_name);
        dest.writeString(this.category_pinyin);
    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.category_code = in.readInt();
        this.category_name = in.readString();
        this.category_pinyin = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
