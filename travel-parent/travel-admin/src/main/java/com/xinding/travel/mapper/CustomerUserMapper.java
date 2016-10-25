package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.CustomerUser;

@Repository
public interface CustomerUserMapper {
	
	List<CustomerUser> customerUserList(Map p);
	
	int AddcustomerUser(CustomerUser customerUser);
	
	CustomerUser findCustomerUser(Long customerId, String account, Long roleId);
	
	CustomerUser findCustomerUserById(Long id);
	
	int updateCustomerUser(CustomerUser customerUser);
	
}

