package com.xinding.travel.mapper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.OrderMobile;

@Repository
public interface OrderMobileMapper {
	/**
	 * <p>订单详情</p> 
	 * @author dongjun
	 * @date 2016年6月24日 下午3:56:54
	 * @param orderSn
	 * @return
	 * @see
	 */
	OrderMobile orderDetail(String orderSn);
	/**
	 * <p>订单列表</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午1:49:41
	 * @param p
	 * @return
	 * @see
	 */
	List<OrderMobile> list(Map p);
	/**
	 * <p>根据微信openId查询手机号信息</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:06:20
	 * @param code
	 * @return
	 * @see
	 */
	List<OrderMobile> getMobilePhone (Map p);
	
    Integer isRepeatedVeri(String veriNo);
	
	OrderMobile isExistVeri(String veriNo);
	
	Timestamp getCreateTime(String veriNo);
	
	int updateVeriStatus(String veriNo, Date n);
	
	int insertPDAUserId(Long pdaUserId, String veriNo);
	
	List<OrderMobile> orderVerList(Integer pdaUserId);
	
	OrderMobile orderVerDetail(String veriNo);
}
