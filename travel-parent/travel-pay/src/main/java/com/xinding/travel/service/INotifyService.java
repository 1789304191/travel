package com.xinding.travel.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xinding.travel.pojo.AlipayNotifyEntity;

public interface INotifyService {
	/**
	 * <p>保存微信支付记录</p> 
	 * @author dongjun
	 * @date 2016年6月21日 下午1:18:11
	 * @param p
	 * @see
	 */
	void saveWxNotify(Map p);
	
	/**
	 * <p>保存支付宝支付记录</p> 
	 * @author dongjun
	 * @date 2016年6月23日 下午2:47:20
	 * @param p
	 * @see
	 */
	void saveAliNotify(AlipayNotifyEntity alipay);
	
	AlipayNotifyEntity handleNotify(HttpServletRequest request,HttpServletResponse response); 
}
