package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.ScenicProjectMapper;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.ScenicProject;
import com.xinding.travel.service.IScenicProjectService;
import com.xinding.travel.util.BeanUtil;

@Service
public class ScenicProjectService implements IScenicProjectService{
	
	@Autowired 
    private ScenicProjectMapper scenicProjectMapper;

	@Override
	public PagedResult<ScenicProject> scenicProjectByPage(Integer pageNo, Integer pageSize,Map p) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(scenicProjectMapper.scenicProjectList(p));
	}

	@Override
	public void add(ScenicProject sp) {
		// TODO Auto-generated method stub
		this.scenicProjectMapper.add(sp);
	}

	@Override
	public void update(ScenicProject sp) {
		// TODO Auto-generated method stub
		this.scenicProjectMapper.update(sp);
	}

	@Override
	public void delete(ScenicProject sp) {
		// TODO Auto-generated method stub
		this.scenicProjectMapper.delete(sp);
	}

	@Override
	public PagedResult<ScenicProject> scenicProjectListAll(Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(scenicProjectMapper.scenicProjectListAll());
	}

	@Override
	public List<Long> projectPDAuserBind(Long pdaUserId) {
		return scenicProjectMapper.projectPDAuserBind(pdaUserId);
	}
	
}
