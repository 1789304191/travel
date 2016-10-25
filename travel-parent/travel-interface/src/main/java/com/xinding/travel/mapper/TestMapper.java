package com.xinding.travel.mapper;

import java.util.Map;

public interface TestMapper {
	
	void insert(Map msg);
	
	Map select();
	
	void update(String num);
	
	void delete();

}
