package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.WhyRoleMapper;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.WhyCustomerUserRole;
import com.xinding.travel.pojo.WhyRole;
import com.xinding.travel.service.IWhyRoleService;
import com.xinding.travel.util.BeanUtil;

@Service("WhyRoleService")
public class WhyRoleService implements IWhyRoleService {

	@Autowired 
    private WhyRoleMapper whyRoleMapper;
	
	@Override
	public PagedResult<WhyRole> userByPage(Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);  //startPage是告诉拦截器说我要开始分页了。分页参数是这两个。
		return BeanUtil.toPagedResult(whyRoleMapper.roleList(null));
	}

	@Override
	public void setPrivilege(Map p) {
		whyRoleMapper.setPrivilege(p);		
	}

	@Override
	public List<WhyRole> roleList(Map p) {
		return whyRoleMapper.roleList(p);
	}
	
	@Override
	public List<Map> findRoleList() {
		return whyRoleMapper.findRoleList();
	}

	@Override
	public void addRole(WhyRole role) {
		whyRoleMapper.addRole(role);
	}
	
	@Override
	public void deleteRole(WhyRole role) {
		whyRoleMapper.updateRole(role);
	}
	
	@Override
	public void updateRole(WhyRole role) {
		whyRoleMapper.updateRole(role);
	}

	@Override
	public List<WhyRole> findRoleById(Map p) {
		return whyRoleMapper.findRoleById(p);
	}

}
