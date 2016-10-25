package com.xinding.travel.service;

import java.util.Map;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.UserStatics;

public interface IUserStaticsService {
	
	PagedResult<UserStatics> userStaticsByPage(Integer pageNo, Integer pageSize,Map p);

}
