package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinding.travel.mapper.WHYCustomerUserRoleMapper;
import com.xinding.travel.pojo.WhyCustomerUserRole;
import com.xinding.travel.service.IWhyCustomerUserRoleService;

@Service
public class WhyCustomerUserRoleService implements IWhyCustomerUserRoleService{

	@Autowired
    private WHYCustomerUserRoleMapper customerUserRoleMapper;
	
	@Override
	public List<WhyCustomerUserRole> customerUserRoleList(Map p) {
		return customerUserRoleMapper.customerUserRoleList(p);
	}
	
	@Override
	public int Addrole(WhyCustomerUserRole whyCustomerUserRole) {
		return customerUserRoleMapper.Addrole(whyCustomerUserRole);
	}
	
	@Override
	public int deleteCustomerUser(Long customerUserId) {
		return customerUserRoleMapper.deleteCustomerUser(customerUserId);
	}
}
