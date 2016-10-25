package com.xinding.travel.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.UserStatics;
import com.xinding.travel.service.IUserStaticsService;

@Controller  
@RequestMapping("/admin/user-statistics")
public class UserStatisticsController extends BaseController{
	
	@Autowired
	private IUserStaticsService userStatisticsService;
	
	@RequestMapping(value = "/userStatisticsGrid", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("all")
	public String userStatisticsGrid(@RequestBody Map map) {  
		logger.info("分页查询用户信息列表请求入参：pageNumber{},pageSize{}",map.get("pageNo"),map.get("pageSize"));
		try {
			Map p=new HashMap();
			Integer pageNo=null;
			Integer pageSize=null;
			if(map.get("pageNo")!=null){
				pageNo=Integer.valueOf(map.get("pageNo").toString());
			}
			if(map.get("pageSize")!=null){
				pageSize=Integer.valueOf(map.get("pageSize").toString());
			}
			PagedResult<UserStatics> pageResult = userStatisticsService.userStaticsByPage(pageNo,
					pageSize,p);
    	    return responseSuccess(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
		
    }  

}
