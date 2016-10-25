package com.xinding.travel.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.MessageMobile;
import com.xinding.travel.service.IMessageMobileService;
import com.xinding.travel.util.Base64;
import com.xinding.travel.util.Constant;
import com.xinding.travel.util.GenerateCode;
import com.xinding.travel.util.HttpSender;
import com.xinding.travel.util.Message;

@Controller
@RequestMapping(value="/messageMobile")
public class MessageMobileController extends BaseController {
	
	@Autowired
	private IMessageMobileService messageMobileService;
	
	@RequestMapping(value="/sendMessage", method= RequestMethod.GET)
    @ResponseBody
	public Message sendMessage(String mobilePhone,String projectCode){
		Message msg = new Message();
		try {
			// 系统当前时间
			long nowTime = System.currentTimeMillis() / 1000;
			Map p = new HashMap();
			p.put("mobilePhone", mobilePhone);
			p.put("projectCode", projectCode);
			
			List<MessageMobile> list = messageMobileService.list(p);
			// 验证验证码是否正确
			if (!list.isEmpty()) {
				// 验证码更新的最新时间
				long lastSendTime = list.get(0).getCreateTime().getTime()/1000;
				// 二者时间差
				long timeDif = nowTime - lastSendTime;
				// 60s之内同一个号码不能重复请求
				if (60 > timeDif) {
					msg.setCode(Constant.INTEGER_NEG_ONE);
					msg.setRequestFlag(false);
					msg.setMesssage("对不起，一分钟以内不能重复发送验证码");
					return msg;
				}
			}
			
			String sendCode = GenerateCode.randomNumber(0, 6);
			String content = "短信验证码:"+sendCode+",有效时间为30分钟，请不要告诉别人哦";
			String status = HttpSender.sendMessage(mobilePhone, content);
			MessageMobile mm = new MessageMobile();
			mm.setMobilePhone(mobilePhone);
			mm.setSendCode(sendCode);
			mm.setMsg(content);
			mm.setStatus(status);
			mm.setCreateTime(new Timestamp(System.currentTimeMillis()));
			mm.setProjectCode(projectCode);
			
			UUID uuid = UUID.randomUUID();
			String token = uuid.toString();
			token = Base64.encode(token);
			mm.setToken(token);
			messageMobileService.save(mm);
			
			if(Constant.STRING_ZERO.equals(status)){
				msg.setRequestFlag(true);
				msg.setMesssage("短信发送成功");
			}else{
				msg.setCode(Constant.INTEGER_NEG_TWO);
				msg.setRequestFlag(false);
				msg.setMesssage("短信发送失败");
			}
			
			
		} catch (Exception e) {
			msg.setCode(Constant.INTEGER_NEG_THREE);
			msg.setRequestFlag(false);
			msg.setMesssage("系统异常");
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午4:21:10
	 * @param p
	 * @return
	 * @see
	 */
	@RequestMapping(value="/getMessage", method= RequestMethod.POST)
    @ResponseBody
	public Message getMessage(@RequestBody Map p){
		Message msg = new Message();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			List<MessageMobile> list = messageMobileService.list(p);
			if(list.isEmpty()){
				msg.setCode(Constant.INTEGER_NEG_ONE);
				msg.setRequestFlag(false);
				msg.setMesssage("验证码不正确");
				return msg;
			}
			// 系统当前时间
			long nowTime = System.currentTimeMillis() / 1000;
			// 验证码更新的最新时间
			long lastSendTime = list.get(0).getCreateTime().getTime()/1000;
			// 二者时间差
			long timeDif = nowTime - lastSendTime;
			// 30分钟失效
			if (1800 < timeDif) {
				msg.setCode(Constant.INTEGER_NEG_TWO);
				msg.setRequestFlag(false);
				msg.setMesssage("对不起，验证码已失效，请重新发送");
				return msg;
			}
			data.put("token", list.get(0).getToken());
			
			msg.setRequestFlag(true);
			msg.setResponseEntiy(data);
			msg.setMesssage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
}
