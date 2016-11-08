package com.xinding.travel.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.WHYCustomer;
import com.xinding.travel.pojo.WHYRegion;
import com.xinding.travel.service.ICustomerService;

@Controller  
@RequestMapping("/admin/customer")
public class CustomerController extends BaseController {
	@Autowired
	private ICustomerService customerService;
	
	@RequestMapping(value = "/customerGrid", method = { RequestMethod.GET })
	@ResponseBody
	public String customerGrid(Integer pageNo,Integer pageSize) {
		try {
			PagedResult<WHYCustomer> pageResult = customerService.customerByPage(pageNo, pageSize);
    	    return responseSuccess(pageResult);
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	/**
	 * 查询父菜单
	 * @return
	 */
	@RequestMapping(value="/findCustomerNameList")
    @ResponseBody
    @SuppressWarnings("all")
    public List<Map> findCustomerNameList() {
		try {
			System.out.println("liguoxiong");
			return customerService.findCustomerNameList();
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 更新驻户
	 * @return
	 */
	@RequestMapping(value="/update")
    @ResponseBody
    @SuppressWarnings("all")
    public String update(@RequestBody WHYCustomer customer) {
		try {
			List<WHYCustomer> list=customerService.selectCustomer(customer);
			if(list!=null&&list.size()!=0){
				return showErrorJson("客户编码已存在");
			}
			customerService.update(customer);
			return showSuccessJson("修改成功");
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return showErrorJson("修改失败");
    }

	/**
	 * 删除驻户
	 * @return
	 */
	@RequestMapping(value="/delete")
    @ResponseBody
    @SuppressWarnings("all")
    public String delete(@RequestBody WHYCustomer customer) {
		try {
			Timestamp now=new Timestamp(System.currentTimeMillis());
			customer.setStatus(new Integer(0));
			customer.setLastUpdatedDatetime(now);
			customer.setLastUpdatedUserId(this.getLoginedUser().getUserId());
			customerService.delete(customer);
			return showSuccessJson("删除成功");
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return showErrorJson("删除失败");
    }
	
	/**
	 * 新增驻户
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody WHYCustomer customer) {
		try {
			List<WHYCustomer> list=customerService.selectCustomer(customer);
			if(list!=null&&list.size()!=0){
				return showErrorJson("客户编码已存在");
			}
			customer.setStatus(new Integer(2));
			customer.setCreateUserId(this.getLoginedUser().getUserId());
			customer.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
			customerService.add(customer);
			return showSuccessJson("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return showErrorJson("添加失败");
	}
}
