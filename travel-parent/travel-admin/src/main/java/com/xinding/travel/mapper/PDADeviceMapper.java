package com.xinding.travel.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.PDADevice;

@Repository
public interface PDADeviceMapper {
	
	List<PDADevice> PDADeviceList();
	
	int add(PDADevice pdaDevice);
	
    int update(PDADevice pdaDevice);
    
    int delete(Long id);
	
}

