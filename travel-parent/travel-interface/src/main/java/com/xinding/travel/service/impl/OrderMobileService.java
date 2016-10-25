package com.xinding.travel.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.OrderMobileMapper;
import com.xinding.travel.mapper.PDAUserModelMapper;
import com.xinding.travel.pojo.OrderMobile;
import com.xinding.travel.pojo.PDAUser;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IOrderMobileService;
import com.xinding.travel.util.BeanUtil;
import com.xinding.travel.util.Message;

@Service
public class OrderMobileService implements IOrderMobileService{
	
	@Autowired 
    private OrderMobileMapper orderMobileMapper;
	@Autowired
	private PDAUserModelMapper pdaUserModelMapper;

	@Override
	public OrderMobile orderDetail(String orderSn) {
		// TODO Auto-generated method stub
		return orderMobileMapper.orderDetail(orderSn);
	}

	@Override
	public PagedResult<OrderMobile> pages(Map p) {
		Integer pageNo = Integer.valueOf(String.valueOf(p.get("pageNo")));
		Integer pageSize = Integer.valueOf(String.valueOf(p.get("pageSize")));
		
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(orderMobileMapper.list(p));
	}
	/**
	 * <p>根据微信openId查询手机号信息</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:06:20
	 * @param code
	 * @return
	 * @see
	 */
	@Override
	public List<OrderMobile> getMobilePhone(Map p) {
		return orderMobileMapper.getMobilePhone(p);
	}

	@Override
	public Message checkoutVeri(String veriNo, Long pdaUserId, String token) {
		Message message = new Message();
		PDAUser pdaUser = pdaUserModelMapper.PDAUserById(pdaUserId);
		if(pdaUser.getToken() != null && pdaUser.getToken().equals(token)) {
			OrderMobile orderMobile = orderMobileMapper.isExistVeri(veriNo);
			if(orderMobile != null) {
				int j = orderMobileMapper.isRepeatedVeri(veriNo);
				if(j == 1) {
					message.setRequestFlag(false);
					message.setMesssage("此单号重复使用");
				} else {
					Timestamp createTime =  orderMobileMapper.getCreateTime(veriNo);
					Date c = null;
					if(createTime != null) {
						c = new Date(createTime.getTime());
					}
					Timestamp now = new Timestamp(System.currentTimeMillis());
					
					Date n = new Date(now.getTime());
					Date n1 = new Date();
					n1 = now;
					SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
					if(df.format(c).equals(df.format(n))) {
						//修改订单状态
						orderMobileMapper.updateVeriStatus(veriNo, n1);
						orderMobileMapper.insertPDAUserId(pdaUserId, veriNo);
						Map p = new HashMap();
						p.put("num", orderMobile.getNum());
						p.put("sn", orderMobile.getOrderSn());
						SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if(orderMobile.getCreateTime() != null) {
							p.put("time", d.format(orderMobile.getCreateTime()));
						}
						p.put("status", orderMobile.getStatus());
						p.put("name", orderMobile.getName());
						p.put("veriNo", orderMobile.getVerificateNo());
						p.put("pdaUser", pdaUser.getAccount());
						message.setResponseEntiy(p);
						message.setRequestFlag(true);
						message.setMesssage("成功");
					} else {
						message.setRequestFlag(false);
						message.setMesssage("此单号已过期");
					}
				}
			} else {
				message.setRequestFlag(false);
				message.setMesssage("此单号不存在");
			}
		} else {
			message.setMesssage("登录异常，token不一致");
			message.setRequestFlag(false);
		}
		
		return message;
	}

	@Override
	public Message orderVerList(Integer status) {
		Message message = new Message();
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<OrderMobile> orders = orderMobileMapper.orderVerList(status);
		for(OrderMobile order : orders) {
			order.setTime(d.format(order.getCreateTime()));
		}
		Map p = new HashMap();
		p.put("order", orders);
		message.setMesssage("查询成功");
		message.setRequestFlag(true);
		message.setResponseEntiy(p);
		return message;
	}

	@Override
	public Message orderVerDetail(String veriNo) {
		OrderMobile order = orderMobileMapper.orderVerDetail(veriNo);
		Map p = new HashMap();
		p.put("name", order.getName());
		p.put("sn", order.getOrderSn());
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(order.getCreateTime() != null) {
			p.put("createtime",d.format(order.getCreateTime()));
		}
		p.put("amount", order.getNum() * order.getPrice());
		p.put("num", order.getNum());
		p.put("price", order.getPrice());
		p.put("tel", order.getMobilePhone());
		if(order.getScannTime() != null) {
			p.put("scannTime", d.format(order.getScannTime()));
		}
		p.put("status", order.getStatus());
		p.put("pdauser", order.getPdaname());
		Message message = new Message();
		message.setMesssage("查询核销详情成功");
		message.setRequestFlag(true);
		message.setResponseEntiy(p);
		return message;
	}

}
