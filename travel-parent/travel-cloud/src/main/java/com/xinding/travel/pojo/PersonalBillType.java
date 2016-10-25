package com.xinding.travel.pojo;

import java.sql.Timestamp;

/**
 * 个人票据类型
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dongjun,date:2016年7月6日 下午5:03:22,content:TODO </p>
 * @author dongjun
 * @date 2016年7月6日 下午5:03:22
 * @since
 * @version
 */
public class PersonalBillType {

	private Long id;
	//企业名称
	private String compay;
	//金额
	private Double amount;
	//驻户Id
	private Long customerId;
	//发布者
	private Long createUserId;
	
	private Timestamp createDatetime;
	//发布者
	private Long lastUpdatedUserId;
	
	private Timestamp lastUpdatedDatetime;
	//状态
	private Integer status;
	
	
	public Timestamp getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}
	public Long getLastUpdatedUserId() {
		return lastUpdatedUserId;
	}
	public void setLastUpdatedUserId(Long lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}
	public Timestamp getLastUpdatedDatetime() {
		return lastUpdatedDatetime;
	}
	public void setLastUpdatedDatetime(Timestamp lastUpdatedDatetime) {
		this.lastUpdatedDatetime = lastUpdatedDatetime;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompay() {
		return compay;
	}
	public void setCompay(String compay) {
		this.compay = compay;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}


	
}
