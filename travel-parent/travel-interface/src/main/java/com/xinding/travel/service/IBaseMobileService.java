package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

public interface IBaseMobileService {
	
	/**
	 * 根据customerId查询驻户信息
	 * @author dongjun
	 * @date 2016年7月5日 上午11:27:43
	 * @param customerId
	 * @return
	 * @see
	 */
	List<Map> customerList(String customerId);
}
