package com.xinding.travel.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.OrderMapper;
import com.xinding.travel.pojo.Order;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IOrderService;
import com.xinding.travel.util.BeanUtil;
@Service
public class OrderService implements IOrderService{
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public PagedResult<Order> OrderByPage(Map p) {
		Integer pageNo = Integer.valueOf(String.valueOf(p.get("pageNo")));
		Integer pageSize = Integer.valueOf(String.valueOf(p.get("pageSize")));
 		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(orderMapper.orderList(p));
	}

	@Override
	public Double amountMoney(Map p) {
		// TODO Auto-generated method stub
		return orderMapper.amountMoney(p);
	}
 
}
