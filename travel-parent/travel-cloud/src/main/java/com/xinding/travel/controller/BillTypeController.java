package com.xinding.travel.controller;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.CompayBillType;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IBillTypeService;
import com.xinding.travel.util.Constant;

@Controller  
@RequestMapping("/admin/billType")
public class BillTypeController extends BaseController{

	@Autowired
	private IBillTypeService billTypeService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/billTypeGrid", method= RequestMethod.POST)
	@ResponseBody
	public String billTypeGrid(@RequestBody Map p) {
		try {
			if(this.getLoginedUser().getUserId()==1){
				p.put("createUserId",null);
				p.put("customerId", null);
			}else{
				p.put("createUserId",this.getLoginedUser().getUserId());
				p.put("customerId",this.getLoginedUser().getCustomerId());
			}
			PagedResult<CompayBillType> pageResult = billTypeService.pages(p);
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
	public String add(@RequestBody CompayBillType ct) {
		try {
			
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("companyTax", ct.getCompanyTax());
//			List<CompayBillType> list = billTypeService.compayBillTypeListByTax(map);
//			if(!list.isEmpty()){
//				return showErrorJson("企业税号不能重复");
//			}
			ct.setStatus(Constant.INTEGER_ONE);
			ct.setCreateUserId(this.getLoginedUser().getUserId());
			ct.setCustomerId(this.getLoginedUser().getCustomerId());
			ct.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
			this.billTypeService.addCompayBill(ct);
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
	public String update(@RequestBody CompayBillType ct) {
		try {
			ct.setLastUpdatedDatetime(new Timestamp(System.currentTimeMillis()));
			ct.setLastUpdatedUserId(this.getLoginedUser().getUserId());
			this.billTypeService.updateCompayBill(ct);
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
	public String delete(@RequestBody CompayBillType ct) {
		try {
			ct.setStatus(Constant.INTEGER_ZERO);
			this.billTypeService.deleteCompayBill(ct);
			return showSuccessJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
}
