package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface BaseMobileMapper {

	/**
	 * 根据customerId查询驻户信息
	 * @author dongjun
	 * @date 2016年7月5日 上午11:27:43
	 * @param customerId
	 * @return
	 * @see
	 */
	List<Map> customerList(String projectCode);
}
