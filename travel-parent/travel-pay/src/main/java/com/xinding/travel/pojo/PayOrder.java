package com.xinding.travel.pojo;

import java.sql.Timestamp;

public class PayOrder {
	private Integer id;
	private String orderSn;
	private Integer status;
	private Long memberId;
	private String openId;
	private Long scenicProjectId;
	private Integer num;
	
	// 订单总额
	private double price;
	// 订单总额
	private double orderAmount;
	private Integer payWayId;
	private Timestamp createTime;
	private String mobilePhone;
	private String code;
	private String qrcodeStr;
	private String qrcodePic;
	private Long customerId;
	private Integer goodsNum;
	private String deliveryId;
	private String projectCode;
	private String verificateNo;
	
	public String getVerificateNo() {
		return verificateNo;
	}
	public void setVerificateNo(String verificateNo) {
		this.verificateNo = verificateNo;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	

	
}
