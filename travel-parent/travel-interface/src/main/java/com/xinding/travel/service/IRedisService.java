package com.xinding.travel.service;

public interface IRedisService {
	
	String get(String key);
	
	Long  setnx(String key,String value);
	
	void set(String key,String value);
	
	String getSet(String key,String value);
	
	void del(String key);
	
}
