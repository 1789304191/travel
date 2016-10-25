package com.xinding.travel.service;

import java.util.Map;

public interface ITestService {
	
	void insert(Map msg);
	
	Map select();
	
	void updateStore(String num);
	
	void updateStore1(String num);
	
	void updateStore2(String num);
	
	void delete();

}
