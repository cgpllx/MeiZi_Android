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

    @SerializedName("category_en_name")
    private String category_en_name;

    @SerializedName("category_icon")
    private String category_icon;

    public String getCategory_icon() {
        return category_icon;
    }

    public void setCategory_icon(String category_icon) {
        this.category_icon = category_icon;
    }

    public String getCategory_en_name() {
        return category_en_name;
    }

    public void setCategory_en_name(String category_en_name) {
        this.category_en_name = category_en_name;
    }



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

    public Category() {
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
        dest.writeString(this.category_en_name);
        dest.writeString(this.category_icon);
    }

    protected Category(Parcel in) {
        this.category_code = in.readInt();
        this.category_name = in.readString();
        this.category_pinyin = in.readString();
        this.category_en_name = in.readString();
        this.category_icon = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
