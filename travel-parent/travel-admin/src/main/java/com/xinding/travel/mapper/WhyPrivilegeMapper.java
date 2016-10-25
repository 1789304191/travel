package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.Menu;
import com.xinding.travel.pojo.WhyPrivilege;

@Repository
@SuppressWarnings("all")
public interface WhyPrivilegeMapper {

	List<Map> privilegeList();
	
	List<Map> privilegesParent(Map p);
	
	List<Map> privilegesChildren(Map p);
	
	List<WhyPrivilege> privilegesThird(Map p);
	
	List<Map> privilegesAll();
	
}
