package com.xinding.travel.service;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.ScenicProject;

public interface IScenicProjectMobileService {
	
	PagedResult<ScenicProject> scenicProjectByPage(Integer pageNo,Integer pageSize,String projectCode);
	
	ScenicProject detail(Integer id);
}
