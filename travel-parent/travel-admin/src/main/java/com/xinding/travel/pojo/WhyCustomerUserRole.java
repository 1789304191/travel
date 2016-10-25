package com.xinding.travel.pojo;

import java.sql.Timestamp;
import java.util.Date;

public class WhyCustomerUserRole  {
	private Long id;
	private Long customerUserId;
	private Long roleId;
	private Integer status;
	private Long createUserId;
	private Timestamp createDatetime;
	private Long lastUpdatedUserId;
	private Timestamp lastUpdatedDatetime;
	
	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}
	public void setLastUpdatedDatetime(Timestamp lastUpdatedDatetime) {
		this.lastUpdatedDatetime = lastUpdatedDatetime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerUserId() {
		return customerUserId;
	}
	public void setCustomerUserId(Long customerUserId) {
		this.customerUserId = customerUserId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateDatetime() {
		return createDatetime;
	}
	 
	public Long getLastUpdatedUserId() {
		return lastUpdatedUserId;
	}
	public void setLastUpdatedUserId(Long lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}
	public Date getLastUpdatedDatetime() {
		return lastUpdatedDatetime;
	}
}
