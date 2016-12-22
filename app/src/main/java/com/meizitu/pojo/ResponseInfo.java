package com.meizitu.pojo;

import com.google.gson.annotations.SerializedName;

import cc.easyandroid.easycore.EAResult;

public class ResponseInfo<T> implements EAResult {
    @SerializedName("code")
    private String code;
    @SerializedName("desc")
    private String desc;
    @SerializedName("data")
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
