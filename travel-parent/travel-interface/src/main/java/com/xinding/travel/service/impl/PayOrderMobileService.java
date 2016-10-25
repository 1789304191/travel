package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinding.travel.mapper.BaseMobileMapper;
import com.xinding.travel.mapper.PayOrderMobileMapper;
import com.xinding.travel.pojo.MessagePay;
import com.xinding.travel.pojo.PayOrder;
import com.xinding.travel.service.IPayOrderService;
@Service
public class PayOrderMobileService implements IPayOrderService{
	@Autowired
	private PayOrderMobileMapper payOrderMobileMapper;
	/**
	 * <p>根据微信授权code查询订单信息</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午3:57:36
	 * @param code
	 * @return
	 * @see
	 */
	@Override
	public List<PayOrder> payOrderListByCode(String code) {
		// TODO Auto-generated method stub
		return payOrderMobileMapper.payOrderListByCode(code);
	}
	/**
	 * <p>保存订单</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午3:59:37
	 * @param ord
	 * @see
	 */
	@Override
	public void savePayOrder(PayOrder ord) {
		// TODO Auto-generated method stub
		payOrderMobileMapper.savePayOrder(ord);
	}
	/**
	 * <p>更新订单信息</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午3:59:50
	 * @param p
	 * @see
	 */
	@Override
	public void updateOrderByOrderSn(Map p) {
		// TODO Auto-generated method stub
		payOrderMobileMapper.updateOrderByOrderSn(p);
	}
	/**
	 * <p>根据订单号查询订单信息</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午3:59:18
	 * @param orderSn
	 * @return
	 * @see
	 */
	@Override
	public PayOrder getPayOrder(String orderSn) {
		// TODO Auto-generated method stub
		return payOrderMobileMapper.getPayOrder(orderSn);
	}
	/**
	 * <p>更新商品数量</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午4:03:29
	 * @param id
	 * @see
	 */
	@Override
	public void updateScenicSalesNum(Long id) {
		// TODO Auto-generated method stub
		payOrderMobileMapper.updateScenicSalesNum(id);
	}
	/**
	 * <p>根据id查询商品详情</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午4:23:36
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	public List<Map> scenicDetail(Long id) {
		// TODO Auto-generated method stub
		return payOrderMobileMapper.scenicDetail(id);
	}
	@Override
	public PayOrder getTopPayOrder() {
		// TODO Auto-generated method stub
		return payOrderMobileMapper.getTopPayOrder();
	}
	@Override
	public List<MessagePay> msgList(Map p) {
		// TODO Auto-generated method stub
		return payOrderMobileMapper.msgList(p);
	}

}
