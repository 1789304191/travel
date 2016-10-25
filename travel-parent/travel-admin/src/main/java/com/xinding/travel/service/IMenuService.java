package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.Menu;
import com.xinding.travel.pojo.PagedResult;

@SuppressWarnings("all")
public interface IMenuService {
	
	List<Menu> menuList(Map p);
	
	public PagedResult<Menu> menuByPage(Integer pageNo, Integer pageSize,String text);
	
	void menuUpdate(Menu m);
	
	void menuAdd(Menu m);
	
	void menuDelete(Map p);

}
