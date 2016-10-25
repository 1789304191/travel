package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.UserStatics;

@Repository
public interface UserStaticsMobileMapper {
	
	void add(UserStatics us);
	
	List<UserStatics> userStaticsList(Map m);

}
