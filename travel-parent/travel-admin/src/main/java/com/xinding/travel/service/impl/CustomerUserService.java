package com.xinding.travel.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.CustomerUserMapper;
import com.xinding.travel.pojo.CustomerUser;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.ICustomerUserService;
import com.xinding.travel.util.BeanUtil;

@Service
public class CustomerUserService implements ICustomerUserService{

	@Autowired
    private CustomerUserMapper customerUserMapper;
	
	@Override
	@SuppressWarnings("all")
	public PagedResult<CustomerUser> customerUserByPage(Integer pageNo, Integer pageSize,String name) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		Map m=new HashMap();
		m.put("name",name);
		return BeanUtil.toPagedResult(customerUserMapper.customerUserList(m));
	}

	@Override
	public int AddcustomerUser(CustomerUser customerUser) {
		return customerUserMapper.AddcustomerUser(customerUser);
	}

	@Override
	public CustomerUser findCustomerUser(Long customerId, String account, Long roleId) {
		return customerUserMapper.findCustomerUser(customerId, account, roleId);
	}
	
	@Override
	public CustomerUser findCustomerUserById(Long id) {
		return customerUserMapper.findCustomerUserById(id);
	}

	@Override
	public int updateCustomerUser(CustomerUser customerUser) {
		return customerUserMapper.updateCustomerUser(customerUser);
	}
}
