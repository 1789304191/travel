package com.xinding.travel.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.UserStaticsMapper;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.UserStatics;
import com.xinding.travel.service.IUserStaticsService;
import com.xinding.travel.util.BeanUtil;

@Service
public class UserStaticsService implements IUserStaticsService {

	@Autowired
    private UserStaticsMapper userStaticsMapper;

	@Override
	public PagedResult<UserStatics> userStaticsByPage(Integer pageNo, Integer pageSize,Map p) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(userStaticsMapper.userStaticsList(p));
	}


}
