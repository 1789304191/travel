package com.xinding.travel.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinding.travel.alipay.util.AlipayNotify;
import com.xinding.travel.mapper.NotifyMapper;
import com.xinding.travel.pojo.AlipayNotifyEntity;
import com.xinding.travel.service.INotifyService;
@Service
public class NotifyService implements INotifyService{
	public static Logger logger=Logger.getLogger(NotifyService.class);
	@Autowired
	private NotifyMapper notifyMapper;
	
	/**
	 * <p>保存微信支付记录</p> 
	 * @author dongjun
	 * @date 2016年6月21日 下午1:18:11
	 * @param p
	 * @see
	 */
	@Override
	public void saveWxNotify(Map p) {
		// TODO Auto-generated method stub
		notifyMapper.saveWxNotify(p);
	}

	@Override
	public void saveAliNotify(AlipayNotifyEntity alipay) {
		// TODO Auto-generated method stub
		notifyMapper.saveAliNotify(alipay);
	}
	@Override
	public AlipayNotifyEntity handleNotify(HttpServletRequest request, HttpServletResponse response) {
		
		try{
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			logger.info("支付宝异步通信，requestParams: " + requestParams.toString());
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			logger.info("支付宝异步通信，params: " + params.toString());

			if(AlipayNotify.verify(params)){//验证成功
				logger.info("异步通知验证成功");
				//notify_time
				String notify_time = new String(request.getParameter("notify_time").getBytes("ISO-8859-1"),"UTF-8");
				//notify_type
				String notify_type = new String(request.getParameter("notify_type").getBytes("ISO-8859-1"),"UTF-8");
				//notify_id
				String notify_id = new String(request.getParameter("notify_id").getBytes("ISO-8859-1"),"UTF-8");
				//sign_type
				String sign_type = new String(request.getParameter("sign_type").getBytes("ISO-8859-1"),"UTF-8");
				//sign
				String sign = new String(request.getParameter("sign").getBytes("ISO-8859-1"),"UTF-8");
				
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
				
				String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"),"UTF-8");
				//payment_type
				String payment_type = new String(request.getParameter("payment_type").getBytes("ISO-8859-1"),"UTF-8");
				//支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
				//交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
				//gmt_create
				String gmt_create = new String(request.getParameter("gmt_create").getBytes("ISO-8859-1"),"UTF-8");
				//seller_id
				String seller_email = new String(request.getParameter("seller_email").getBytes("ISO-8859-1"),"UTF-8");
				//buyer_email
				String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");
				//seller_id
				String seller_id = new String(request.getParameter("seller_id").getBytes("ISO-8859-1"),"UTF-8");
				//buyer_id
				String buyer_id = new String(request.getParameter("buyer_id").getBytes("ISO-8859-1"),"UTF-8");
				//price
				String price = new String(request.getParameter("price").getBytes("ISO-8859-1"),"UTF-8");
				//total_fee
				String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
				//quantity
				String quantity = new String(request.getParameter("quantity").getBytes("ISO-8859-1"),"UTF-8");
				//body
				String body = new String(request.getParameter("body").getBytes("ISO-8859-1"),"UTF-8");
				//gmt_payment
				String gmt_payment = "";
				
				String gmt_close = "";
				//discount
				String discount = "";
				//is_total_fee_adjust
				String is_total_fee_adjust = "";
				//use_coupon
				String use_coupon = "";
				//refund_status
				String refund_status = "";
				//gmt_refund
				String gmt_refund = "";
				//
				if(StringUtils.isNotBlank(request.getParameter("gmt_payment"))){
					gmt_payment = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"),"UTF-8");
				}
				//
				if(StringUtils.isNotBlank(request.getParameter("gmt_close"))){
					gmt_close = new String(request.getParameter("gmt_close").getBytes("ISO-8859-1"),"UTF-8");
				}
				//
				if(StringUtils.isNotBlank(request.getParameter("discount"))){
					discount = new String(request.getParameter("discount").getBytes("ISO-8859-1"),"UTF-8");
				}
				//
				if(StringUtils.isNotBlank(request.getParameter("is_total_fee_adjust"))){
					is_total_fee_adjust = new String(request.getParameter("is_total_fee_adjust").getBytes("ISO-8859-1"),"UTF-8");
				}
				//
				if(StringUtils.isNotBlank(request.getParameter("use_coupon"))){
					use_coupon = new String(request.getParameter("use_coupon").getBytes("ISO-8859-1"),"UTF-8");
				}
				//
				if(StringUtils.isNotBlank(request.getParameter("refund_status"))){
					refund_status = new String(request.getParameter("refund_status").getBytes("ISO-8859-1"),"UTF-8");
				}
				//
				if(StringUtils.isNotBlank(request.getParameter("gmt_refund"))){
					gmt_refund = new String(request.getParameter("gmt_refund").getBytes("ISO-8859-1"),"UTF-8");
				}
				//aliPayVo
				AlipayNotifyEntity aliPayVo = new AlipayNotifyEntity();
				//notify_time
				aliPayVo.setNotify_time(notify_time);
				//notify_type
				aliPayVo.setNotify_type(notify_type);
				//notify_id
				aliPayVo.setNotify_id(notify_id);
				//sign_type
				aliPayVo.setSign_type(sign_type);
				//sign
				aliPayVo.setSign(sign);
				//out_trade_no
				aliPayVo.setOut_trade_no(out_trade_no);
				//subject
				aliPayVo.setSubject(subject);
				//payment_type
				aliPayVo.setPayment_type(payment_type);
				//trade_no
				aliPayVo.setTrade_no(trade_no);
				//trade_status
				aliPayVo.setTrade_status(trade_status);
				//gmt_create
				aliPayVo.setGmt_create(gmt_create);
				//gmt_payment
				aliPayVo.setGmt_payment(gmt_payment);
				//gmt_close
				aliPayVo.setGmt_close(gmt_close);
				//seller_email
				aliPayVo.setSeller_email(seller_email);
				//buyer_email
				aliPayVo.setBuyer_email(buyer_email);
				//seller_id
				aliPayVo.setSeller_id(seller_id);
				//buyer_id
				aliPayVo.setBuyer_id(buyer_id);
				//price
				aliPayVo.setPrice(price);
				//total_fee
				aliPayVo.setTotal_fee(total_fee);
				//quantity
				aliPayVo.setQuantity(quantity);
				//body
				aliPayVo.setBody(body);
				//discount
				aliPayVo.setDiscount(discount);
				//is_total_fee_adjust
				aliPayVo.setIs_total_fee_adjust(is_total_fee_adjust);
				//use_coupon
				aliPayVo.setUse_coupon(use_coupon);
				//refund_status
				aliPayVo.setRefund_status(refund_status);
				//gmt_refund
				aliPayVo.setGmt_refund(gmt_refund);
				
				return aliPayVo;
				
			}else{//验证失败
				logger.info("异步通知验证失败");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
		
	}


}
