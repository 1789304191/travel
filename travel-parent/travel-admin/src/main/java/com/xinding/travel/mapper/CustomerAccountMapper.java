package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.CustomerAccount;

@Repository
public interface CustomerAccountMapper {
	
	List<CustomerAccount> selectCustomerAccount(Map m);
	
	int AddcustomerAccount(CustomerAccount customerAccount);
	
	CustomerAccount findCustomerAccount(Map m);
	
	int updateCustomerAccount(CustomerAccount customerAccount);
	
}
