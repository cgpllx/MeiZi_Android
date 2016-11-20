package com.meizitu.pojo;

import com.google.gson.annotations.Expose;

import cc.easyandroid.easycore.EAResult;

public class ResponseInfo<T> implements EAResult{
	@Expose
	private String code;
	@Expose
	private String desc;
	@Expose
	private T data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public boolean isSuccess() {
		return "C0000".equals(code);
	}

	@Override
	public String getEADesc() {
		return desc;
	}

	@Override
	public String getEACode() {
		return code;
	}
}
