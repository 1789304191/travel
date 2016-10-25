package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.WhyCustomerUserRole;

public interface IWhyCustomerUserRoleService {
	
	List<WhyCustomerUserRole> customerUserRoleList(Map p);

	int Addrole(WhyCustomerUserRole whyCustomerUserRole);
	
	int deleteCustomerUser(Long customerUserId);
}
