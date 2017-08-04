package com.meizitu.pojo;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.meizitu.banner.IBanner;

public class Image implements IBanner {
	@SerializedName("imageurl")
	private String imageurl;
	@SerializedName("groupimageinfo_id")
	private int groupimageinfo_id;

	boolean loaded;

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public String getImageurl() {
		return imageurl;
	}


	public int getGroupimageinfo_id() {
		return groupimageinfo_id;
	}

	@Override
	public String getImageUrl() {
		return imageurl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.imageurl);
		dest.writeInt(this.groupimageinfo_id);
		dest.writeByte(loaded ? (byte) 1 : (byte) 0);
	}

	public Image() {
	}

	protected Image(Parcel in) {
		this.imageurl = in.readString();
		this.groupimageinfo_id = in.readInt();
		this.loaded = in.readByte() != 0;
	}

	public static final Creator<Image> CREATOR = new Creator<Image>() {
		public Image createFromParcel(Parcel source) {
			return new Image(source);
		}

		public Image[] newArray(int size) {
			return new Image[size];
		}
	};
}
