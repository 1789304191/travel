package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.CustomerAccount;

public interface ICustomerAccountService {
	
	List<CustomerAccount> selectCustomerAccount(Map m);
	
	int AddcustomerAccount(CustomerAccount customerAccount);
	
	CustomerAccount findCustomerAccount(Map m);
	
	int updateCustomerAccount(CustomerAccount customerAccount);
	
}
