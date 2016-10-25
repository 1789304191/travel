package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.CustomerMapper;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.WHYCustomer;
import com.xinding.travel.service.ICustomerService;
import com.xinding.travel.util.BeanUtil;

@Service
public class CustomerService implements ICustomerService{

	@Autowired 
    private CustomerMapper customerMapper;
	
	@Override
	public List<WHYCustomer> selectCustomer(WHYCustomer customer) {
		return customerMapper.selectCustomer(customer);
	}

	@Override
	public PagedResult<WHYCustomer> customerByPage(Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(customerMapper.customerList());
	}
	
	@Override
	public List<Map> findCustomerNameList(){
		return customerMapper.findCustomerNameList();
	}

	@Override
	public void update(WHYCustomer customer) {
		customerMapper.update(customer);
	}

	@Override
	public void delete(WHYCustomer customer) {
		customerMapper.update(customer);
	}

	@Override
	public void add(WHYCustomer customer) {
		customerMapper.add(customer);
	}

}
