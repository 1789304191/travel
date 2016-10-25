package com.xinding.travel.service;

import com.xinding.travel.pojo.CustomerUser;
import com.xinding.travel.pojo.PagedResult;

public interface ICustomerUserService {
	
	PagedResult<CustomerUser> customerUserByPage(Integer pageNo,Integer pageSize,String name);
	
	int AddcustomerUser(CustomerUser customerUser);
	
	CustomerUser findCustomerUser(Long customerId, String account, Long roleId);
	
	CustomerUser findCustomerUserById(Long id);
	
	int updateCustomerUser(CustomerUser customerUser);
}
