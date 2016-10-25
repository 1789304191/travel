package com.xinding.travel.service;

import com.xinding.travel.util.Message;

public interface IPDAUserModelService {
	public Message PDALogin(String account, String password, String deviceNo);
	
	public Message pdaUserDetail(Long id);
	
}
