package com.xinding.travel.pojo;

import java.sql.Timestamp;
import java.util.Date;

public class OrderMobile {
	private Integer id;
	private String orderSn;
	private Integer status;
	private Long memberId;
	private String openId;
	private Long scenicProjectId;
	private Integer num;
	
	// 订单总额
	private double orderAmount;
	// 订单总额
	private double price;
	private Integer payWayId;
	private Timestamp createTime;
	private String mobilePhone;
	private String code;
	private String qrcodeStr;
	private String qrcodePic;
	private Long customerId;
	private Integer goodsNum;
	private String deliveryId;
	private String verificateNo;
	private String projectCode;
	private Integer pdaUserId;
	private String name;
	private String description;
	private String time;
	private Date scannTime;
	private String pdaname;
	private String buyTime;
	
	
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Date getScannTime() {
		return scannTime;
	}
	public void setScannTime(Date scannTime) {
		this.scannTime = scannTime;
	}
	public String getPdaname() {
		return pdaname;
	}
	public void setPdaname(String pdaname) {
		this.pdaname = pdaname;
	}
	public String getVerificateNo() {
		return verificateNo;
	}
	public void setVerificateNo(String verificateNo) {
		this.verificateNo = verificateNo;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public Integer getPdaUserId() {
		return pdaUserId;
	}
	public void setPdaUserId(Integer pdaUserId) {
		this.pdaUserId = pdaUserId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getScenicProjectId() {
		return scenicProjectId;
	}
	public void setScenicProjectId(Long scenicProjectId) {
		this.scenicProjectId = scenicProjectId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Integer getPayWayId() {
		return payWayId;
	}
	public void setPayWayId(Integer payWayId) {
		this.payWayId = payWayId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getQrcodeStr() {
		return qrcodeStr;
	}
	public void setQrcodeStr(String qrcodeStr) {
		this.qrcodeStr = qrcodeStr;
	}
	public String getQrcodePic() {
		return qrcodePic;
	}
	public void setQrcodePic(String qrcodePic) {
		this.qrcodePic = qrcodePic;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	

	
}
