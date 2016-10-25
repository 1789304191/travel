package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.WHYCustomer;

@Repository
public interface CustomerMapper {
	
	List<WHYCustomer> selectCustomer(WHYCustomer customer);
	
	List<WHYCustomer> customerList();

	List<Map> findCustomerNameList();
	
	void update(WHYCustomer customer);
	
	void add(WHYCustomer customer);

}
