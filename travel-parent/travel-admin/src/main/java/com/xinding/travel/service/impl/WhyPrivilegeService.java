package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinding.travel.mapper.WhyPrivilegeMapper;
import com.xinding.travel.pojo.WhyPrivilege;
import com.xinding.travel.service.IWhyPrivilegeService;

@Service
public class WhyPrivilegeService implements IWhyPrivilegeService {

	@Autowired 
    private WhyPrivilegeMapper privilegeMapper;
	
	@Override
	public List<Map> privilegeList() {
		return privilegeMapper.privilegeList();
	}

	@Override
	public List<Map> privilegesParent(Map p) {
		return privilegeMapper.privilegesParent(p);
	}
	
	@Override
	public List<Map> privilegesChildren(Map p) {
		return privilegeMapper.privilegesChildren(p);
	}

	@Override
	public List<WhyPrivilege> privilegesThird(Map p) {
		return privilegeMapper.privilegesThird(p);
	}

	@Override
	public List<Map> privilegesAll() {
		return privilegeMapper.privilegesAll();
	}

}
