package com.xinding.travel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.MenuMapper;
import com.xinding.travel.pojo.Menu;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IMenuService;
import com.xinding.travel.util.BeanUtil;

@Service
public class MenuService implements IMenuService {

	@Autowired
    private MenuMapper menuMapper;
	
	@Override
	public List<Menu> menuList(Map p) {
		return menuMapper.menuList(p);
	}
	
	@Override
	@SuppressWarnings("all")
	public PagedResult<Menu> menuByPage(Integer pageNo, Integer pageSize,String text) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);  //startPage是告诉拦截器说我要开始分页了。分页参数是这两个。
		Map m=new HashMap();
		m.put("text", text);
		return BeanUtil.toPagedResult(menuMapper.menuList(m));
	}

	@Override
	public void menuUpdate(Menu m) {
		menuMapper.menuUpdate(m);
	}

	@Override
	public void menuAdd(Menu m) {
		menuMapper.menuAdd(m);
	}

	@Override
	public void menuDelete(Map p) {
		menuMapper.menuDelete(p);
	}

}
