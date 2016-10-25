package com.xinding.travel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinding.travel.mapper.VersionModelMapper;
import com.xinding.travel.pojo.WHYVersion;
import com.xinding.travel.service.IVersionMobileService;
import com.xinding.travel.util.Message;


@Service
public class VersionMobileService implements IVersionMobileService{

	@Resource
	private VersionModelMapper versionModelMapper;

	@Override
	public Message checkVersion(Float versionNo) {
		List<WHYVersion> versionList = versionModelMapper.versionList();
		Message message = new Message();
		Map p = new HashMap();
		for(WHYVersion version : versionList) {
			//如果后台版本大于当前版本,需要提示下载，返回url路径
			if(Float.valueOf(version.getVersionNo()) > versionNo) {
				p.put("downloadurl", version.getDownloadUrl());
				message.setMesssage("有新版本");
				message.setRequestFlag(true);
				message.setResponseEntiy(p);
			} else {
				message.setMesssage("当前版本是最新版本");
				message.setRequestFlag(true);
			}
		}
		return message;
	}
	
	
}
