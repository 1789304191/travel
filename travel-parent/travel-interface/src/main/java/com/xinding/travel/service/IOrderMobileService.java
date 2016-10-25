package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.OrderMobile;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.util.Message;

public interface IOrderMobileService {
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
	 * <p>订单列表分页</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:09:21
	 * @param p
	 * @return
	 * @see
	 */
	PagedResult<OrderMobile> pages(Map p);
	
	/**
	 * <p>根据微信openId查询手机号信息</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:06:20
	 * @param code
	 * @return
	 * @see
	 */
	List<OrderMobile> getMobilePhone (Map p);
	
	/**
	 * <p>检验核销单是否正常</p> 
	 * @author liupeiqiang
	 * @date 2016年6月27日
	 * @param veriNo
	 * @return
	 * @see
	 */
	public Message checkoutVeri(String veriNo, Long pdaUserId, String token);
	
	public Message orderVerList(Integer status);
	
	public Message orderVerDetail(String veriNo);
}
