package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.WHYCustomer;

public interface ICustomerService {
	
	List<WHYCustomer> selectCustomer(WHYCustomer customer);
	
	PagedResult<WHYCustomer> customerByPage(Integer pageNo,Integer pageSize);

	List<Map> findCustomerNameList();
	
	void update(WHYCustomer customer);
	
	void delete(WHYCustomer customer);
	
	void add(WHYCustomer customer);

}
