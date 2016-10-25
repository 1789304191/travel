package com.xinding.travel.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.Order;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IOrderService;
import com.xinding.travel.util.Constant;

@Controller  
@RequestMapping("/admin/order")
public class OrderController extends BaseController{

	@Autowired
	private IOrderService orderService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/orderGrid", method= RequestMethod.POST)
	@ResponseBody
	public String orderGrid(@RequestBody Map p) {
		try {
			if(this.getLoginedUser().getUserId()==1){
//				p.put("createUserId",null);
				p.put("customerId", null);
			}else{
//				p.put("createUserId",this.getLoginedUser().getUserId());
				p.put("customerId",this.getLoginedUser().getCustomerId());
			}
			String payId = String.valueOf(p.get("payId"));
			String status = String.valueOf(p.get("status"));
			if(Constant.STRING_NEG_ONE.equals(payId)){
				p.put("payId", null);
			}
			if(Constant.STRING_NEG_ONE.equals(status)){
				p.put("status", null);
			}
			if(!"".equals(p.get("startDatetime")) && null != (p.get("startDatetime"))){
				String startDatetime = String.valueOf(p.get("startDatetime"))+Constant.BEGIN_TIME;
				p.put("startDatetime", startDatetime);
			}
			if(!"".equals(p.get("endDatetime")) && null != (p.get("endDatetime"))){
				String endDatetime = String.valueOf(p.get("endDatetime"))+Constant.END_TIME;
				p.put("endDatetime", endDatetime);
			}
			
			
			PagedResult<Order> pageResult = orderService.OrderByPage(p);
			//订单总额
			Double amountMoney = orderService.amountMoney(p) == null ?0:orderService.amountMoney(p);
			pageResult.setAmountMoney(amountMoney);
			return responseSuccess(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
}
