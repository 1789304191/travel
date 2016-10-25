package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.PDAUser;

@Repository
public interface PDAUserMapper {
	
	List<PDAUser> PDAUserList(Map p);
	
	int add(PDAUser pdaUser);
	
    int update(PDAUser pdaUser);
    
    int delete(Long id);
	
    PDAUser findByAccount(String account);
    
    int addEmployeeNo(String employeeNo, String account);
    
    int bind(Long scenicId, Long pdaUserId);
    
    int deleteProjectPDAuserId(Long pdaUserId);
    
}

