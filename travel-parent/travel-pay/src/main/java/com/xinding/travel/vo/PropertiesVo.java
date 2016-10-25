package com.xinding.travel.vo;
/**
 * TODO(支付相关配置文件)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dongjun,date:2016年6月23日 下午1:43:15,content:TODO </p>
 * @author dongjun
 * @date 2016年6月23日 下午1:43:15
 * @since
 * @version
 */
public class PropertiesVo {
	
	//订单标识
	private  String orderIdentify;
	//支付宝异步通知
	private  String alipay_notify_url;
	//微信支付异步通知
	private  String wxpay_notify_url;
	
	public String getOrderIdentify() {
		return orderIdentify;
	}
	public void setOrderIdentify(String orderIdentify) {
		this.orderIdentify = orderIdentify;
	}
	public String getAlipay_notify_url() {
		return alipay_notify_url;
	}
	public void setAlipay_notify_url(String alipay_notify_url) {
		this.alipay_notify_url = alipay_notify_url;
	}
	public String getWxpay_notify_url() {
		return wxpay_notify_url;
	}
	public void setWxpay_notify_url(String wxpay_notify_url) {
		this.wxpay_notify_url = wxpay_notify_url;
	}
	
	
	
	

}
