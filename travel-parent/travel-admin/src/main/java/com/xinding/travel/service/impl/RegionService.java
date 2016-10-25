package com.xinding.travel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.WhyRegionMapper;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.WHYRegion;
import com.xinding.travel.service.IRegionService;
import com.xinding.travel.util.BeanUtil;

@Service
public class RegionService implements IRegionService {

	@Autowired
    private WhyRegionMapper whyRegionMapper;
	
	@Override
	@SuppressWarnings("all")
	public PagedResult<WHYRegion> regionByPage(Integer pageNo, Integer pageSize,String name) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		Map m=new HashMap();
		m.put("name",name);
		return BeanUtil.toPagedResult(whyRegionMapper.regionList(m));
	}

	@Override
	public void add(WHYRegion r) {
		whyRegionMapper.add(r);
	}

	@Override
	public void delete(WHYRegion r) {
		whyRegionMapper.update(r);
	}

	@Override
	public void update(WHYRegion r) {
		whyRegionMapper.update(r);
	}

	@Override
	@SuppressWarnings("all")
	public List<Map> regionSelectList() {
		List<Map> region=new ArrayList<Map>();
		List<WHYRegion> list=whyRegionMapper.regionList(null);
		for(WHYRegion r:list){
			Map m=new HashMap();
			m.put("regionId", r.getId());
			m.put("name",r.getName());
			region.add(m);
		}
		return region;
	}

	
}
