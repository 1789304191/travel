package com.xinding.travel.service;

import java.util.Map;

import com.xinding.travel.pojo.PDAUser;
import com.xinding.travel.pojo.PagedResult;

public interface IPDAUserService {
	
	PagedResult<PDAUser> pdaUserByPage(Map p);
	
	int add(PDAUser pdaUser);
	
    int update(PDAUser pdaUser);
    
    int delete(Long id);
    
    PDAUser findByAccount(String account);
    
    int addEmployeeNo(String employeeNo, String account);
    
    int bind(Long scenicId, Long pdaUserId);
    
    int deleteProjectPDAuserId(Long pdaUserId);
}
