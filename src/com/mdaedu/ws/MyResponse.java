package com.mdaedu.ws;

import java.util.List;

public class MyResponse <T>{
	private List<T> rows;
	private Integer total;
	private Integer page;
	private Integer records;
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> result) {
		this.rows = result;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	
}
