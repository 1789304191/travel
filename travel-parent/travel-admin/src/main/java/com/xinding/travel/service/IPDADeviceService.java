package com.xinding.travel.service;

import com.xinding.travel.pojo.PDADevice;
import com.xinding.travel.pojo.PagedResult;

public interface IPDADeviceService {
	
	PagedResult<PDADevice> pdaUserByPage(Integer pageNo,Integer pageSize);
	
	int add(PDADevice pdaDevice);
	
    int update(PDADevice pdaDevice);
    
    int delete(Long id);
	
}
