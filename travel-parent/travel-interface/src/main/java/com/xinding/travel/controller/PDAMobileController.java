package com.xinding.travel.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.service.IPDAUserModelService;
import com.xinding.travel.util.Message;

@Controller
@RequestMapping("/PDAMobile")
public class PDAMobileController extends BaseController{
	
	@Autowired 
	private IPDAUserModelService pdaUserModelService;
	
	@RequestMapping(value = "/PDALogin",method= RequestMethod.POST)
	@ResponseBody
	public Message PDALogin(@RequestBody Map p) {
		String account = (String) p.get("account");
		String password = (String) p.get("password");
		String deviceNo = (String) p.get("deviceno");
		return pdaUserModelService.PDALogin(account, password, deviceNo);
	}
	
	@RequestMapping(value = "/PDAUserDetail",method= RequestMethod.POST)
	@ResponseBody
	public Message PDAUserDetail(@RequestBody Map p) {
		Long id = Long.valueOf((String) p.get("pdaUserId"));
		Message message = pdaUserModelService.pdaUserDetail(id);
		return message;
	}
}
