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
import com.xinding.travel.pojo.ScenicProject;
import com.xinding.travel.service.IScenicProjectService;

@Controller  
@RequestMapping("/admin/scenic-project")
public class ScenicProjectController  extends BaseController{

	@Autowired
	private IScenicProjectService iScenicProjectService;
	
	@RequestMapping(value="/scenicProjectGrid", method= RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("all")
    public String ScenicProjectGrid(@RequestBody Map map) {
		logger.info("分页查询用户信息列表请求入参：pageNumber{},pageSize{}",map.get("pageNo"),map.get("pageSize"));
		try {
			Map p=new HashMap();
			if(this.getLoginedUser().getUserId()==1){
				p.put("createUserId",null);
				p.put("customerId", null);
			}else{
				p.put("createUserId",this.getLoginedUser().getUserId());
				p.put("customerId",this.getLoginedUser().getCustomerId());
			}
			p.put("name",map.get("name"));
			p.put("status",map.get("status"));
			p.put("startDatetime",map.get("stime"));
			p.put("endDatetime",map.get("etime"));
			Integer pageNo=null;
			Integer pageSize=null;
			if(map.get("pageNo")!=null){
				pageNo=Integer.valueOf(map.get("pageNo").toString());
			}
			if(map.get("pageSize")!=null){
				pageSize=Integer.valueOf(map.get("pageSize").toString());
			}
			PagedResult<ScenicProject> pageResult = iScenicProjectService.scenicProjectByPage(pageNo,
					pageSize,p);
    	    return responseSuccess(pageResult);
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
    }
	
	
	/**
	 * 新增
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	@ResponseBody
	public String add(@RequestBody ScenicProject sp) {
		try {
			sp.setStatus(new Integer(2));
			sp.setCreateUserId(this.getLoginedUser().getUserId());
			sp.setCustomerId(this.getLoginedUser().getCustomerId());
			sp.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
			sp.setSalesNum(new Integer(0));
			this.iScenicProjectService.add(sp);
			return showSuccessJson("添加成功");
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(@RequestBody ScenicProject sp) {
		try {
			sp.setStatus(new Integer(0));
			this.iScenicProjectService.delete(sp);
			return showSuccessJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public String update(@RequestBody ScenicProject sp) {
		try {
			sp.setLastUpdatedDatetime(new Timestamp(System.currentTimeMillis()));
			sp.setLastUpdatedUserId(this.getLoginedUser().getUserId());
			this.iScenicProjectService.update(sp);
			return showSuccessJson("更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/listAll", method = { RequestMethod.GET })
	@ResponseBody
	public String scenicProjectListAll(Integer pageNo,Integer pageSize, Long pdaUserId) {
		try {
			PagedResult<ScenicProject> pageResult =  iScenicProjectService.scenicProjectListAll(pageNo, pageSize);
			List<Long> eventIds = iScenicProjectService.projectPDAuserBind(pdaUserId);
		    pageResult.setEventIds(eventIds);
    	    return responseSuccess(pageResult);
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
}
