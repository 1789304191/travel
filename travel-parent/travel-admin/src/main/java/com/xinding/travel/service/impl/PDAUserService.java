package com.xinding.travel.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.PDAUserMapper;
import com.xinding.travel.pojo.PDAUser;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IPDAUserService;
import com.xinding.travel.util.BeanUtil;

@Service
public class PDAUserService implements IPDAUserService{
	
	@Autowired
    private PDAUserMapper pdaUserMapper;
	
	@Override
	public PagedResult<PDAUser> pdaUserByPage(Map p) {
		Integer pageNo = Integer.valueOf(String.valueOf(p.get("pageNo")));
		Integer pageSize = Integer.valueOf(String.valueOf(p.get("pageSize")));
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(pdaUserMapper.PDAUserList(p));
	}

	@Override
	public int add(PDAUser pdaUser) {
		return pdaUserMapper.add(pdaUser);
	}

	@Override
	public int update(PDAUser pdaUser) {
		return pdaUserMapper.update(pdaUser);
	}

	@Override
	public int delete(Long id) {
		return pdaUserMapper.delete(id);
	}

	@Override
	public PDAUser findByAccount(String account) {
		return pdaUserMapper.findByAccount(account);
	}

	@Override
	public int addEmployeeNo(String employeeNo, String account) {
		return pdaUserMapper.addEmployeeNo(employeeNo, account);
	}

	@Override
	public int bind(Long scenicId, Long pdaUserId) {
		return pdaUserMapper.bind(scenicId, pdaUserId);
	}
	
	@Override
	public int deleteProjectPDAuserId(Long pdaUserId) {
		return pdaUserMapper.deleteProjectPDAuserId(pdaUserId);
	}
}
