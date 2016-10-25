package com.xinding.travel.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinding.travel.mapper.PDAUserModelMapper;
import com.xinding.travel.pojo.PDADevice;
import com.xinding.travel.pojo.PDAUser;
import com.xinding.travel.service.IPDAUserModelService;
import com.xinding.travel.util.Base64;
import com.xinding.travel.util.Message;


@Service
public class PDAUserMobileService implements IPDAUserModelService{

	@Resource
	private PDAUserModelMapper pdaUserModelMapper;
	
	/**
	 * PDA登录
	 */
	@Override
	public Message PDALogin(String account, String password, String deviceNo) {
		Message msg = new Message();
		//查询PDA设备是否生效
		int num = pdaUserModelMapper.deviceList(deviceNo);
		//生效,进入登入
		if(num == 1) {
			PDAUser pdaUser = pdaUserModelMapper.PDAUserByAccount(account);
			if(pdaUser != null) {
				if(pdaUser.getPassword().equals(password)) {
					UUID uuid = UUID.randomUUID();
					String token = uuid.toString();
					token = Base64.encode(token);
					pdaUserModelMapper.updateToken(token, account, password);
					Map p = new HashMap();
					p.put("account", pdaUser.getAccount());
					p.put("token", token);
					p.put("pdaUserId", pdaUser.getId());
					msg.setResponseEntiy(p);
					msg.setRequestFlag(true);
					msg.setMesssage("登录成功");
				} else {
					msg.setRequestFlag(false);
					msg.setMesssage("密码错误");
				}
			} else {
				msg.setRequestFlag(false);
				msg.setMesssage("用户不存在");
			}
		//不生效
		} else {
			//查询此设备是否被禁用，禁用直接报错给前台
			int j = pdaUserModelMapper.invalidDeviceList(deviceNo);
			if(j == 1) {
				msg.setRequestFlag(false);
				msg.setMesssage("PDA已禁用");
			//未禁用直接插入设备信息
			} else {
				PDADevice pdaDevice = new PDADevice();
				Timestamp now = new Timestamp(System.currentTimeMillis());
				pdaDevice.setCreateDatetime(now);
				pdaDevice.setDeviceNo(deviceNo);
				pdaDevice.setStatus(new Integer(2));
				pdaUserModelMapper.insertDevice(pdaDevice);
			}
		}
		return msg;
	}

	@Override
	public Message pdaUserDetail(Long id) {
		List<Map> pdaUsers = pdaUserModelMapper.pdaUserDetail(id);
		Message message = new Message();
		if(pdaUsers.size() > 0) {
			Map p = new HashMap();
			p.put("pic", pdaUsers.get(0).get("firstPic"));
			p.put("pdaUserName",pdaUsers.get(0).get("pdaname"));
			p.put("employeeNo", pdaUsers.get(0).get("employeeNo"));
			String storeNames = "";
			for(Map user : pdaUsers) {
				if(user.get("storename") != null) {
					storeNames =  storeNames + "," + user.get("storename");
				} else {
					storeNames = (String) user.get("storename");
				}
				p.put("storeName", storeNames);
				
			}
			message.setMesssage("获取PDA用户成功");
			message.setRequestFlag(true);
			message.setResponseEntiy(p);
			return message;
		} else {
			message.setMesssage("无数据");
			message.setRequestFlag(true);
			return message;
		}
		
		
	}
}
