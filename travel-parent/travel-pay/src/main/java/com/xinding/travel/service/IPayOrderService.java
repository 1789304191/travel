package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.MessagePay;
import com.xinding.travel.pojo.PayOrder;

public interface IPayOrderService {
	
	/**
	 * <p>根据微信授权code查询订单信息</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午3:57:36
	 * @param code
	 * @return
	 * @see
	 */
	List<PayOrder> payOrderListByCode(String code);
	/**
	 * <p>根据订单号查询订单信息</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午3:59:18
	 * @param orderSn
	 * @return
	 * @see
	 */
	PayOrder getPayOrder(String orderSn);
	/**
	 * <p>查询最新订单</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午3:59:18
	 * @param orderSn
	 * @return
	 * @see
	 */
	PayOrder getTopPayOrder();
	/**
	 * <p>保存订单</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午3:59:37
	 * @param ord
	 * @see
	 */
	void savePayOrder(PayOrder ord);
	/**
	 * <p>更新订单信息</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午3:59:50
	 * @param p
	 * @see
	 */
	void updateOrderByOrderSn(Map p);
	/**
	 * <p>更新商品数量</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午4:03:29
	 * @param id
	 * @see
	 */
	void updateScenicSalesNum(Long id);
	/**
	 * <p>根据id查询商品详情</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午4:23:36
	 * @param id
	 * @return
	 * @see
	 */
	List<Map> scenicDetail(Long id);
	
	List<MessagePay> msgList(Map p);
	
}
