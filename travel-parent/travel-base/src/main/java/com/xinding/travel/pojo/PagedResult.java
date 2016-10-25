package com.xinding.travel.pojo;

import java.util.List;

public class PagedResult<T> extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<T> dataList;// 数据

	private long pageNo;// 当前页

	private long pageSize;// 条数

	private long total;// 总条数

	private long pages;// 总页面数目
	
	private double amountMoney;// 总页面数目
	
	private List<Long> eventIds;
	
	public List<Long> getEventIds() {
		return eventIds;
	}

	public void setEventIds(List<Long> eventIds) {
		this.eventIds = eventIds;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	public double getAmountMoney() {
		return amountMoney;
	}

	public void setAmountMoney(double amountMoney) {
		this.amountMoney = amountMoney;
	}
	
	
}
