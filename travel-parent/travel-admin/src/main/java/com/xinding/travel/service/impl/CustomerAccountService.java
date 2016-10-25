package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinding.travel.mapper.CustomerAccountMapper;
import com.xinding.travel.pojo.CustomerAccount;
import com.xinding.travel.service.ICustomerAccountService;

@Service
public class CustomerAccountService implements ICustomerAccountService{

	@Autowired 
    private CustomerAccountMapper customerAccountMapper;
	
	@Override
	public List<CustomerAccount> selectCustomerAccount(Map m) {
		return customerAccountMapper.selectCustomerAccount(m);
	}
	
	@Override
	public CustomerAccount findCustomerAccount(Map m) {
		return customerAccountMapper.findCustomerAccount(m);
	}
	
	@Override
	public int AddcustomerAccount(CustomerAccount customerAccount){
		return customerAccountMapper.AddcustomerAccount(customerAccount);
	}
	
	@Override
	public int updateCustomerAccount(CustomerAccount customerAccount){
		return customerAccountMapper.updateCustomerAccount(customerAccount);
	}
}
