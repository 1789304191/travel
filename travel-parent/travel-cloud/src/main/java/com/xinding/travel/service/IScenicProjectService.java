package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.ScenicProject;

public interface IScenicProjectService {
	
	PagedResult<ScenicProject> scenicProjectByPage(Integer pageNo,Integer pageSize,Map p);
	
	void add(ScenicProject sp);

	void update(ScenicProject sp);
	
	void delete(ScenicProject sp);
	
	PagedResult<ScenicProject> scenicProjectListAll(Integer pageNo, Integer pageSize);
	
	List<Long> projectPDAuserBind(Long pdaUserId);
}
