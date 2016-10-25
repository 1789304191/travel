package com.xinding.travel.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.service.IWhyPrivilegeService;
import com.xinding.travel.service.IWhyRoleService;

@Controller  
@RequestMapping("/admin/privilege")
public class WhyPrivilegeController extends BaseController{
	
	
	@Autowired
	private IWhyPrivilegeService privilegeService;
	
	@Autowired
	private IWhyRoleService whyRoleService;
	
	@RequestMapping(value="/privilegeGrid", method= RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("all")
    public List<Map> privilegeGrid(String code) {
		logger.info("查询参数code：", code);
		try {
			List<Map> privileges=privilegeService.privilegeList();
			if(privileges!=null&&privileges.size()!=0&&!"".equals(code)){
				String[] codes = StringUtils.split(code,
                        ",");
				for(int i=0;i<privileges.size();i++){
					for(int j=0;j<codes.length;j++){
						if(privileges.get(i).get("id").equals(Long.valueOf(codes[j]))){
							privileges.get(i).put("checked", true);
//							if(privileges.get(i).get("pId").equals(Long.valueOf(0))){
//								privileges.get(i).put("open", true);
//							}
						}
					}
				}
			}
    	    return privileges;
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	@RequestMapping(value="/setPrivilege", method= RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("all")
    public String setPrivilege(@RequestBody Map p) {
		logger.info("查询参数codes：id", p.get("codes")+"=========="+p.get("id"));
		try {
			whyRoleService.setPrivilege(p);
			return showSuccessJson("设置成功");
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return showErrorJson("设置失败");
    }
	
	/**
	 * 查询父菜单
	 * @return
	 */
	@RequestMapping(value="/privilegesParent")
    @ResponseBody
    @SuppressWarnings("all")
    public List<Map> privilegesParent() {
		try {
			return privilegeService.privilegesParent(null);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 查询所有菜单
	 * @return
	 */
	@RequestMapping(value="/privilegesAll")
    @ResponseBody
    @SuppressWarnings("all")
    public List<Map> privilegesAll() {
		try {
			return privilegeService.privilegesAll();
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	

}
