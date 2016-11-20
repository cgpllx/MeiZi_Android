package com.meizitu.pojo;

import com.google.gson.annotations.Expose;
import com.meizitu.banner.IQfangBanner;

public class Image implements IQfangBanner{
	@Expose
	private String imageurl;

	private int groupimageinfo_id;

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public int getGroupimageinfo_id() {
		return groupimageinfo_id;
	}

	public void setGroupimageinfo_id(int groupimageinfo_id) {
		this.groupimageinfo_id = groupimageinfo_id;
	}


	@Override
	public String getImageUrl() {
		return imageurl;
	}
}
