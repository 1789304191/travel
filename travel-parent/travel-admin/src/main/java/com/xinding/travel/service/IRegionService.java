package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.WHYRegion;

public interface IRegionService {
	
	PagedResult<WHYRegion> regionByPage(Integer pageNo,Integer pageSize,String name);
	
	void add(WHYRegion r);
	
	void delete(WHYRegion r);
	
	void update(WHYRegion r);
	
	List<Map> regionSelectList();
}
