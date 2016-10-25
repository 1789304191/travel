package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinding.travel.mapper.BaseMobileMapper;
import com.xinding.travel.service.IBaseMobileService;
@Service
public class BaseMobileService implements IBaseMobileService{

	@Autowired
	private BaseMobileMapper baseMobileMapper;
	@Override
	public List<Map> customerList(String customerId) {
		// TODO Auto-generated method stub
		return baseMobileMapper.customerList(customerId);
	}

}
