package com.xinding.travel.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.WhyRole;
import com.xinding.travel.service.IWhyRoleService;

@Controller
@RequestMapping("/admin/role_table")
public class WhyRoleController extends BaseController {

	@Autowired
	private IWhyRoleService whyRoleService;

	@RequestMapping(value = "/roleGrid", method = RequestMethod.GET)
	@ResponseBody
	public String roleGrid(Integer pageNo, Integer pageSize) {
		logger.info("分页查询用户信息列表请求入参：pageNumber{},pageSize{}", pageNo, pageSize);
		try {
			PagedResult<WhyRole> pageResult = whyRoleService.userByPage(pageNo, pageSize);
			return responseSuccess(pageResult);
		} catch (Exception e) {
			return responseFail(e.getMessage());
		}
	}

	@RequestMapping(value = "/findRoleList")
	@ResponseBody
	@SuppressWarnings("all")
	public List<Map> findRoleList() {
		return whyRoleService.findRoleList();
	}

	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("all")
	public String add(@RequestBody WhyRole role) {
		try {
			Map m=new HashMap();
			m.put("name", role.getName());
			List<WhyRole> list=whyRoleService.roleList(m);
			if(list!=null&&list.size()!=0){
				return showErrorJson("角色名称重复");
			}
			Timestamp now = new Timestamp(System.currentTimeMillis());
			role.setCreateDatetime(now);
			role.setLastUpdatedDatetime(now);
			role.setLastUpdatedUserId(this.getLoginedUser().getUserId());
			role.setCreateUserId(this.getLoginedUser().getUserId());
			role.setStatus(new Integer(2));
			whyRoleService.addRole(role);
			return showSuccessJson("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return showErrorJson("添加失败");
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("all")
	public String delete(@RequestBody WhyRole role) {
		try {
			role.setStatus(new Integer(0));
			whyRoleService.deleteRole(role);
			return showSuccessJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return showErrorJson("删除失败");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("all")
	public String update(@RequestBody WhyRole role) {
		try {
			Map m=new HashMap();
			m.put("id", role.getId());
			m.put("name", role.getName());
			List<WhyRole> list=whyRoleService.roleList(m);
			if(list!=null&&list.size()!=0){
				return showErrorJson("角色名称重复");
			}
			Timestamp now = new Timestamp(System.currentTimeMillis());
			role.setLastUpdatedDatetime(now);
			role.setLastUpdatedUserId(this.getLoginedUser().getUserId());
			whyRoleService.deleteRole(role);
			return showSuccessJson("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return showErrorJson("修改失败");
	}

}
