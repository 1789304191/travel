package com.xinding.travel.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xinding.travel.mapper.TestMapper;
import com.xinding.travel.service.ITestService;

@Service
public class TestService implements ITestService {

	@Autowired
	private TestMapper testMapper;
	
	@Override
	public void insert(Map msg) {
		testMapper.insert(msg);
	}

	@Override
	public Map select() {
		return testMapper.select();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED )
	public void updateStore(String num) {
		testMapper.update(num);
		updateStore1(num);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete() {
		testMapper.delete();
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void updateStore1(String num) {
		testMapper.update(num);
		Integer.valueOf(null);
	}
	
	@Override
    @Transactional(propagation = Propagation.SUPPORTS)
	public void updateStore2(String num) {
		testMapper.update(num);
		Integer.valueOf(null);
	}

}
