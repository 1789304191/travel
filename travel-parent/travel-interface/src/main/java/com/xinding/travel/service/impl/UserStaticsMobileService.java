package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xinding.travel.mapper.UserStaticsMobileMapper;
import com.xinding.travel.pojo.UserStatics;
import com.xinding.travel.service.IUserStaticsMobileService;

@Service
public class UserStaticsMobileService implements IUserStaticsMobileService {

	@Autowired
    private UserStaticsMobileMapper userStaticsMapper;

	@Override
	public void add(UserStatics us) {
		userStaticsMapper.add(us);
	}

	@Override
	public List<UserStatics> select(Map p) {
		return userStaticsMapper.userStaticsList(p);
	}


}
