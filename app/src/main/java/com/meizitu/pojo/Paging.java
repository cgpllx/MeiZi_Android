package com.meizitu.pojo;

public class Paging<T> {

	private int totalCount;// 总
	private int currentPageNo;// 当前页码
	private int totalPage;// 总页数
	private int pageSize;// 每页数量
	private T data;
	
	

	public Paging(int totalCount, int currentPageNo, int pageSize) {
		super();
		this.totalCount = totalCount;
		this.currentPageNo = currentPageNo;
		this.pageSize = pageSize;
		count();
	}

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
	/**
	 * 根据当前对象中属性值计算并设置相关属性值
	 */
	public void count() {
		// 计算总页数
		int totalPageTemp = this.totalCount / this.pageSize;
		int plus = (this.totalCount % this.pageSize) == 0 ? 0 : 1;
		totalPageTemp = totalPageTemp + plus;
		if(totalPageTemp <= 0) {
			totalPageTemp = 1;
		}
		this.totalPage = totalPageTemp;
		
		// 设置当前页数
		// 总页数小于当前页数，应将当前页数设置为总页数
		if(this.totalPage < this.currentPageNo) {
			this.currentPageNo = this.totalPage;
		}
		// 当前页数小于1设置为1
		if(this.currentPageNo < 1) {
			this.currentPageNo = 1;
		}
		 
	}


}
