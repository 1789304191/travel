package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.WhyCustomerUserRole;

@ResponseBody
public interface WHYCustomerUserRoleMapper {
	
	List<WhyCustomerUserRole> customerUserRoleList(Map p);
	
	int Addrole(WhyCustomerUserRole whyCustomerUserRole);
	
	int deleteCustomerUser(Long customerUserId);

}
