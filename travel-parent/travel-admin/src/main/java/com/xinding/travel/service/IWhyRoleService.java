package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.WhyRole;

public interface IWhyRoleService {
	
	PagedResult<WhyRole> userByPage(Integer pageNo,Integer pageSize);
	
	void setPrivilege(Map p);
	
	List<WhyRole> roleList(Map p);

	List<Map> findRoleList();
	
	void addRole(WhyRole role);
	
	void deleteRole(WhyRole role);
	
	void updateRole(WhyRole role);
	
	List<WhyRole> findRoleById(Map p);
}
