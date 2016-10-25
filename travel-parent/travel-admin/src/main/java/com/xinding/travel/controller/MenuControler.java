package com.xinding.travel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.Menu;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.SessionUser;
import com.xinding.travel.pojo.WhyCustomerUserRole;
import com.xinding.travel.pojo.WhyRole;
import com.xinding.travel.service.IMenuService;
import com.xinding.travel.service.IWhyCustomerUserRoleService;
import com.xinding.travel.service.IWhyPrivilegeService;
import com.xinding.travel.service.IWhyRoleService;

@Controller  
@RequestMapping("/admin/menu")
@SuppressWarnings("all")
public class MenuControler extends BaseController{
	
	@Autowired
	private IMenuService menuService;
	
	@Autowired
	private IWhyCustomerUserRoleService whyCustomerUserRoleService;
	
	@Autowired
	private IWhyRoleService whyRoleService;
	
	@Autowired
	private IWhyPrivilegeService whyPrivilegeService;
	
	@RequestMapping(value = "/menuList")
	@ResponseBody
	@SuppressWarnings("all")
	public List<Map> menuList() {         
		try {
			Subject currentUser = SecurityUtils.getSubject();
			SessionUser loginUser = (SessionUser) currentUser.getPrincipal();
			List<String> roleIds = new ArrayList<String>();
			if(loginUser!=null){
				// 1、超级管理员用户所有权限
				if(loginUser.getUserId()==1L){
					List<Map> privilegeList = whyPrivilegeService.privilegesParent(null);
					for (Map map : privilegeList) {
                    	//根据父菜单查询子菜单
                    	Map child=new HashMap();
                    	child.put("id", map.get("id"));
                    	List<Map> children=whyPrivilegeService.privilegesChildren(child);
                    	map.put("submenu", children);
                    }
                    return privilegeList;
				}
			Map p=new HashMap();
			p.put("customerUserId", loginUser.getUserId());
			List<WhyCustomerUserRole> userRoles=whyCustomerUserRoleService.customerUserRoleList(p);
			for (WhyCustomerUserRole userRole : userRoles) {
                roleIds.add(userRole.getRoleId().toString());
            }
			if (roleIds.size() > 0) {
                List<String> privilegesIds = new ArrayList<String>();
                Map m=new HashMap();
                m.put("ids",roleIds);
                List<WhyRole> roles = whyRoleService.roleList(m);
                for (WhyRole role : roles) {
                    String privilegeCodes = role.getPrivilegeCodes();
                    if (StringUtils.isNotBlank(privilegeCodes)) {
                        String[] arrIds = StringUtils.split(privilegeCodes,
                                ",");
                        for (String id : arrIds) {
                            privilegesIds.add(id);
                        }
                    }
                }
                if (privilegesIds.size() > 0) {
                	Map ps=new HashMap();
                	ps.put("id",privilegesIds);
                    List<Map> privilegeList = whyPrivilegeService.privilegesParent(ps);
                    for (Map map : privilegeList) {
                    	//根据父菜单查询子菜单
                    	Map child=new HashMap();
                    	child.put("id", map.get("id"));
                    	List<Map> children=whyPrivilegeService.privilegesChildren(child);
                    	map.put("submenu", children);
                    }
                    return privilegeList;
                }
			} 
			}
		}catch (Exception e) {
    		e.printStackTrace();
		}
		return null;
	}

	/**
	 * 权限页面
	 */
	@RequestMapping(value = "/menuGrid")
	@ResponseBody
	@SuppressWarnings("all")
	public String permissions(Integer pageNo,Integer pageSize,String text) {
		logger.info("分页查询用户信息列表请求入参：pageNumber{},pageSize{}", pageNo,pageSize);
		try {
			PagedResult<Menu> pageResult =menuService.menuByPage(pageNo, pageSize,text);
    	    return responseSuccess(pageResult);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
	 * 菜单权限修改
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	@SuppressWarnings("all")
	public String menuUpdate(@RequestBody Menu menu) {
		try {
			menuService.menuUpdate(menu);
			return showSuccessJson("修改成功");
    	} catch (Exception e) {
    		e.printStackTrace();
		}
		return showErrorJson("修改失败");
	}
	
	/**
	 * 菜单权限新增
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	@SuppressWarnings("all")
	public String menuAdd(@RequestBody Menu menu) {
		try {
			menuService.menuAdd(menu);
			return showSuccessJson("添加成功");
    	} catch (Exception e) {
    		e.printStackTrace();
		}
		return showErrorJson("添加失败");
	}
	
	/**
	 * 菜单权限删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	@SuppressWarnings("all")
	public String menuDelete(@RequestBody Map p) {
		try {
			menuService.menuDelete(p);
			return showSuccessJson("删除成功");
    	} catch (Exception e) {
    		e.printStackTrace();
		}
		return showErrorJson("删除失败");
	}
}
