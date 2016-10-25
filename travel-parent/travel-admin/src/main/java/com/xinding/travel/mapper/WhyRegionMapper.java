package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.WHYRegion;

@Repository
public interface WhyRegionMapper {
	
	List<WHYRegion> regionList(Map m);
	
	void add(WHYRegion r);
	
	void update(WHYRegion r);

}
