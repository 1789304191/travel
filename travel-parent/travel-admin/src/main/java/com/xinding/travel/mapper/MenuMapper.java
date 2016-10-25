package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.Menu;

@Repository
public interface MenuMapper {
	
	List<Menu> menuList(Map p);
	
	void menuUpdate(Menu m);
	
	void menuAdd(Menu m);
	
	void menuDelete(Map p);
	
}
