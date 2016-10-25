package com.xinding.travel.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.service.IVersionMobileService;
import com.xinding.travel.util.Message;

@Controller
@RequestMapping("/versionMobile")
public class VersionMobileController extends BaseController {
	
	@Autowired 
	private IVersionMobileService versionMobileService;
	/**
	 * <p>根据手机号查询订单列表</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:12:01
	 * @param p
	 * @return
	 * @see
	 */
	@RequestMapping(value="/checkVersion", method= RequestMethod.POST)
    @ResponseBody
    public Message checkVersion(@RequestBody Map p) {
		Float version = Float.valueOf((String)p.get("version"));
		Message message = versionMobileService.checkVersion(version);
		return message;
	}
}
