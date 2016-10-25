package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.ScenicProject;

@Repository
public interface ScenicProjectMapper {

	List<ScenicProject> scenicProjectList(Map p);
	
	void add(ScenicProject sp);
	
	void update(ScenicProject sp);
	
	void delete(ScenicProject sp);
	
	List<ScenicProject> scenicProjectListAll();
	
	List<Long> projectPDAuserBind(Long pdaUserId);
}
