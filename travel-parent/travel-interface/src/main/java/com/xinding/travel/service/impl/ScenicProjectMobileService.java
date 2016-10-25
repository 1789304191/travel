package com.xinding.travel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.ScenicProjectMobileMapper;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.ScenicProject;
import com.xinding.travel.service.IScenicProjectMobileService;
import com.xinding.travel.util.BeanUtil;

@Service
public class ScenicProjectMobileService implements IScenicProjectMobileService{
	
	@Autowired 
    private ScenicProjectMobileMapper scenicProjectMobileMapper;

	@Override
	public PagedResult<ScenicProject> scenicProjectByPage(Integer pageNo, Integer pageSize,String projectCode) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(scenicProjectMobileMapper.list(projectCode));
	}

	@Override
	public ScenicProject detail(Integer id) {
		// TODO Auto-generated method stub
		return scenicProjectMobileMapper.detail(id);
	}

}
