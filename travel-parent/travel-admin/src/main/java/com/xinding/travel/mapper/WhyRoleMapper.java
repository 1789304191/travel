package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.WhyCustomerUserRole;
import com.xinding.travel.pojo.WhyRole;

@Repository
public interface WhyRoleMapper {
	
	List<WhyRole> roleList(Map p);
	
	void setPrivilege(Map p);
	
	List<Map> findRoleList();
	
	void addRole(WhyRole role);
	
	void updateRole(WhyRole role);
	
	List<WhyRole> findRoleById(Map p);

}
