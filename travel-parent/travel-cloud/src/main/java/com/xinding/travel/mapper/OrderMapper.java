package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.Order;
@Repository
public interface OrderMapper {

	/**
	 * 订单列表 
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:26
	 * @param p
	 * @return
	 * @see
	 */
	List<Order> orderList(Map p);
	/**
	 * 订单总额 
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:11
	 * @return
	 * @see
	 */
	Double amountMoney(Map p);
}
