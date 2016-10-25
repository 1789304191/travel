package com.xinding.travel.service;

import java.util.Map;

import com.xinding.travel.pojo.Order;
import com.xinding.travel.pojo.PagedResult;

public interface IOrderService {
	
	PagedResult<Order> OrderByPage(Map p);
	
	/**
	 * 订单总额 
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:11
	 * @return
	 * @see
	 */
	Double amountMoney(Map p);
}
