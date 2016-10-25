package com.xinding.travel.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.PersonalBillType;
import com.xinding.travel.service.IBillTypeService;
import com.xinding.travel.util.Constant;

@Controller  
@RequestMapping("/admin/personalBillType")
public class PersonalBillTypeController extends BaseController{

	@Autowired
	private IBillTypeService billTypeService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/personalBillTypeGrid", method= RequestMethod.POST)
	@ResponseBody
	public String personalBillTypeGrid(@RequestBody Map p) {
		try {
			if(this.getLoginedUser().getUserId()==1){
				p.put("createUserId",null);
				p.put("customerId", null);
			}else{
				p.put("createUserId",this.getLoginedUser().getUserId());
				p.put("customerId",this.getLoginedUser().getCustomerId());
			}
			PagedResult<PersonalBillType> pageResult = billTypeService.personalPages(p);
			return responseSuccess(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	
	/**
	 * 新增
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	@ResponseBody
	public String add(@RequestBody PersonalBillType pt) {
		try {
			BigDecimal bd = new BigDecimal(pt.getAmount().toString());  
			bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
			pt.setAmount(bd.doubleValue());
			pt.setStatus(Constant.INTEGER_ONE);
			pt.setCreateUserId(this.getLoginedUser().getUserId());
			pt.setCustomerId(this.getLoginedUser().getCustomerId());
			pt.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
			this.billTypeService.addPersonalBill(pt);
			return showSuccessJson("添加成功");
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	/**
	 * 更新
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public String update(@RequestBody PersonalBillType pt) {
		try {
			BigDecimal bd = new BigDecimal(pt.getAmount().toString());  
			bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
			pt.setAmount(bd.doubleValue());
			pt.setLastUpdatedDatetime(new Timestamp(System.currentTimeMillis()));
			pt.setLastUpdatedUserId(this.getLoginedUser().getUserId());
			this.billTypeService.updatePersonalBill(pt);
			return showSuccessJson("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(@RequestBody PersonalBillType pt) {
		try {
			pt.setStatus(Constant.INTEGER_ZERO);
			this.billTypeService.deletePersonalBill(pt);
			return showSuccessJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
}
