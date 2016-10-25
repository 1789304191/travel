package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinding.travel.mapper.MessageMobileMapper;
import com.xinding.travel.pojo.MessageMobile;
import com.xinding.travel.service.IMessageMobileService;
@Service
public class MessageMobileService implements IMessageMobileService{
	@Autowired
	private MessageMobileMapper messageMobileMapper;
	/**
	 * <p>保存短信</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:44:22
	 * @param msg
	 * @see
	 */
	@Override
	public void save(MessageMobile msg) {
		// TODO Auto-generated method stub
		messageMobileMapper.save(msg);
	}
	/**
	 * <p>根据手机号，验证码，项目编码查询短信息</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午3:26:42
	 * @param p
	 * @return
	 * @see
	 */
	@Override
	public List<MessageMobile> list(Map p) {
		// TODO Auto-generated method stub
		return messageMobileMapper.list(p);
	}
	@Override
	public List<MessageMobile> listByToken(Map p) {
		// TODO Auto-generated method stub
		return messageMobileMapper.listByToken(p);
	}

}
