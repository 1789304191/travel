package com.xinding.travel.mapper;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.PDADevice;
import com.xinding.travel.pojo.PDAUser;

@Repository
public interface PDAUserModelMapper {
	
	PDAUser PDAUserByAccount(String account);
	
	PDAUser PDAUserById(Long id);
	
	void updateToken(String token, String account, String password);
	
    int deviceList(String deviceNo);
    
    int invalidDeviceList(String deviceNo);
    
    int insertDevice(PDADevice pdaDevice);
    
    List<Map> pdaUserDetail(Long id);
//    List<PDAUser> pdaUserDetail(Long id);
   
}
