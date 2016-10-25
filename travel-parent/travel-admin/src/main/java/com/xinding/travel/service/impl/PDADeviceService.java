package com.xinding.travel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.PDADeviceMapper;
import com.xinding.travel.pojo.PDADevice;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IPDADeviceService;
import com.xinding.travel.util.BeanUtil;

@Service
public class PDADeviceService implements IPDADeviceService{
	
	@Autowired
    private PDADeviceMapper pdaDeviceMapper;
	
	@Override
	public PagedResult<PDADevice> pdaUserByPage(Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(pdaDeviceMapper.PDADeviceList());
	}

	@Override
	public int add(PDADevice pdaDevice) {
		return pdaDeviceMapper.add(pdaDevice);
	}

	@Override
	public int update(PDADevice pdaDevice) {
		return pdaDeviceMapper.update(pdaDevice);
	}

	public int delete(Long id) {
		return pdaDeviceMapper.delete(id);
	} 
}
