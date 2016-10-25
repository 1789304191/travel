package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.UserStatics;

public interface IUserStaticsMobileService {
	
	void add(UserStatics us);
	
	List<UserStatics> select(Map p);

}
