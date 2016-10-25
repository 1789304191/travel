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

import com.xinding.travel.pojo.UserStatics;
import com.xinding.travel.service.IUserStaticsMobileService;

@Controller  
@RequestMapping("/user-statisticsMobile")
public class UserStatisticsMobileController extends BaseController{
	
	@Autowired
	private IUserStaticsMobileService userStatisticsService;
	
	@RequestMapping(value = "/addUserStatistics", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("all")
	public String addUserStatistics(@RequestBody Map m) {  
		try {
			UserStatics us=new UserStatics();
			Timestamp now=new Timestamp(System.currentTimeMillis());
			Map p=new HashMap();
			p.put("deviceNo",m.get("deviceNo"));
			us.setStartTimestamp(now);
			us.setDeviceNo((String)m.get("deviceNo"));
			List<UserStatics> list=userStatisticsService.select(p);
			if(list.size()!=0){
				us.setLastLoginTime(list.get(0).getStartTimestamp());
			}
			userStatisticsService.add(us);
    	    return showErrorJson("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return showErrorJson("添加失败");
		}
		
    }  

}
