package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.WhyPrivilege;

public interface IWhyPrivilegeService {
	
	List<Map> privilegeList();
	
	List<Map> privilegesParent(Map p);
	
	List<Map> privilegesChildren(Map p);
	
	List<WhyPrivilege> privilegesThird(Map p);
	
	List<Map> privilegesAll();
	
	

}
