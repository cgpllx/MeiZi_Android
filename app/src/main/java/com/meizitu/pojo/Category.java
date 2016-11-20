 package com.meizitu.pojo;

import com.google.gson.annotations.Expose;

public class Category {
	@Expose
	private int category_code;
	
	@Expose
	private String category_name;
	
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

}
