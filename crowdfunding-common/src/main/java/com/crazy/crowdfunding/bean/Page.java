package com.crazy.crowdfunding.bean;

import java.util.List;

public class Page<T> {
	
	private List<T> datas;
	
	//当前页号
	private int pageNo;
	
	//总页数，总共分为几页
	private int totalNo;
	
	//总记录数，总共多少条记录
	private int totalSize;
	
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalNo() {
		return totalNo;
	}
	public void setTotalNo(int totalNo) {
		this.totalNo = totalNo;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

}
