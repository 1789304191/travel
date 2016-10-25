package com.xinding.travel.pojo;
/**
 * 支付宝异步通知实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dongjun,date:2016年6月23日 下午3:16:40,content:TODO </p>
 * @author dongjun
 * @date 2016年6月23日 下午3:16:40
 * @since
 * @version
 */
public class AlipayNotifyEntity {
	/**
	 * 订单号
	 */
	private String orderSn;
	/**
	 * 通知时间
	 */
	private String notify_time;
	/**
	 * 通知类型
	 */
	private String notify_type;
	
	/**
	 * 通知校验ID
	 */
	private String notify_id;
	
	/**
	 * 签名方式
	 */
	private String sign_type;

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 商户网站唯一订单号
	 */
	private String out_trade_no;

	/**
	 * 商品名称
	 */
	private String subject;

	/**
	 * 支付类型
	 */
	private String payment_type;

	/**
	 * 交易号
	 */
	private String trade_no;
	
	/**
	 * 交易状态
	 */
	private String trade_status;
	/**
	 * 交易创建时间
	 */
	private String gmt_create;
	/**
	 * 交易付款时间
	 */
	private String gmt_payment;
	/**
	 * 交易关闭时间
	 */
	private String gmt_close;
	/**
	 * 卖家支付宝账号
	 */
	private String seller_email;
	/**
	 * 买家支付宝账号
	 */
	private String buyer_email;
	/**
	 * 卖家支付宝账户号
	 */
	private String seller_id;
	/**
	 * 买家支付宝账户号
	 */
	private String buyer_id;
	/**
	 * 商品单价
	 */
	private String price;
	/**
	 * 交易金额
	 */
	private String total_fee;
	/**
	 * 购买数量
	 */
	private String quantity;
	
	/**
	 * 商品描述
	 */
	private String body;
	
	/**
	 * 折扣
	 */
	private String discount;
	
	/**
	 * 是否调整总价
	 */
	private String is_total_fee_adjust;
	
	/**
	 * 是否使用红包买家
	 */
	private String use_coupon;
	
	/**
	 * 退款状态
	 */
	private String refund_status;
	/**
	 * 退款时间
	 */
	private String gmt_refund;

	public String getOrderSn() {
		return orderSn;
	}



	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}



	public String getGmt_close() {
		return gmt_close;
	}



	public void setGmt_close(String gmt_close) {
		this.gmt_close = gmt_close;
	}



	/**
	 * @return  the notify_time
	 */
	public String getNotify_time() {
		return notify_time;
	}



	/**
	 * @param notify_time the notify_time to set
	 */
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}



	/**
	 * @return  the notify_type
	 */
	public String getNotify_type() {
		return notify_type;
	}



	/**
	 * @param notify_type the notify_type to set
	 */
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}



	/**
	 * @return  the notify_id
	 */
	public String getNotify_id() {
		return notify_id;
	}



	/**
	 * @param notify_id the notify_id to set
	 */
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}



	/**
	 * @return  the sign_type
	 */
	public String getSign_type() {
		return sign_type;
	}



	/**
	 * @param sign_type the sign_type to set
	 */
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}



	/**
	 * @return  the sign
	 */
	public String getSign() {
		return sign;
	}



	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}



	/**
	 * @return  the out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}



	/**
	 * @param out_trade_no the out_trade_no to set
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}



	/**
	 * @return  the subject
	 */
	public String getSubject() {
		return subject;
	}



	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}



	/**
	 * @return  the payment_type
	 */
	public String getPayment_type() {
		return payment_type;
	}



	/**
	 * @param payment_type the payment_type to set
	 */
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}



	/**
	 * @return  the trade_status
	 */
	public String getTrade_status() {
		return trade_status;
	}



	/**
	 * @param trade_status the trade_status to set
	 */
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}



	/**
	 * @return  the seller_id
	 */
	public String getSeller_id() {
		return seller_id;
	}



	/**
	 * @param seller_id the seller_id to set
	 */
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}



	/**
	 * @return  the seller_email
	 */
	public String getSeller_email() {
		return seller_email;
	}



	/**
	 * @param seller_email the seller_email to set
	 */
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}



	/**
	 * @return  the buyer_id
	 */
	public String getBuyer_id() {
		return buyer_id;
	}



	/**
	 * @param buyer_id the buyer_id to set
	 */
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}



	/**
	 * @return  the buyer_email
	 */
	public String getBuyer_email() {
		return buyer_email;
	}



	/**
	 * @param buyer_email the buyer_email to set
	 */
	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}



	/**
	 * @return  the total_fee
	 */
	public String getTotal_fee() {
		return total_fee;
	}



	/**
	 * @param total_fee the total_fee to set
	 */
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}



	/**
	 * @return  the trade_no
	 */
	public String getTrade_no() {
		return trade_no;
	}



	/**
	 * @param trade_no the trade_no to set
	 */
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}



	/**
	 * @return  the quantity
	 */
	public String getQuantity() {
		return quantity;
	}



	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}



	/**
	 * @return  the price
	 */
	public String getPrice() {
		return price;
	}



	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}



	/**
	 * @return  the body
	 */
	public String getBody() {
		return body;
	}



	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}



	/**
	 * @return  the gmt_create
	 */
	public String getGmt_create() {
		return gmt_create;
	}



	/**
	 * @param gmt_create the gmt_create to set
	 */
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}



	/**
	 * @return  the gmt_payment
	 */
	public String getGmt_payment() {
		return gmt_payment;
	}



	/**
	 * @param gmt_payment the gmt_payment to set
	 */
	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}



	/**
	 * @return  the is_total_fee_adjust
	 */
	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}



	/**
	 * @param is_total_fee_adjust the is_total_fee_adjust to set
	 */
	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}



	/**
	 * @return  the use_coupon
	 */
	public String getUse_coupon() {
		return use_coupon;
	}



	/**
	 * @param use_coupon the use_coupon to set
	 */
	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}



	/**
	 * @return  the discount
	 */
	public String getDiscount() {
		return discount;
	}



	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}



	/**
	 * @return  the refund_status
	 */
	public String getRefund_status() {
		return refund_status;
	}



	/**
	 * @param refund_status the refund_status to set
	 */
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}



	/**
	 * @return  the gmt_refund
	 */
	public String getGmt_refund() {
		return gmt_refund;
	}



	/**
	 * @param gmt_refund the gmt_refund to set
	 */
	public void setGmt_refund(String gmt_refund) {
		this.gmt_refund = gmt_refund;
	}

}
