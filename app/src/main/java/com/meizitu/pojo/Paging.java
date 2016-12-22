package com.meizitu.pojo;

import com.google.gson.annotations.SerializedName;

public class Paging<T> {
    @SerializedName("totalCount")
    private int totalCount;// 总
    @SerializedName("currentPageNo")
    private int currentPageNo;// 当前页码
    @SerializedName("totalPage")
    private int totalPage;// 总页数
    @SerializedName("pageSize")
    private int pageSize;// 每页数量
    @SerializedName("data")
    private T data;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
