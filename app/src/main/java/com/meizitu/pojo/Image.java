package com.meizitu.pojo;

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
}
