package com.xinding.travel.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.AlipayNotifyEntity;

@Repository
public interface NotifyMapper {

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
	
	
}
