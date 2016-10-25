package com.xinding.travel.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.CustomerAccount;
import com.xinding.travel.pojo.CustomerUser;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.WhyCustomerUserRole;
import com.xinding.travel.service.ICustomerAccountService;
import com.xinding.travel.service.ICustomerUserService;
import com.xinding.travel.service.IWhyCustomerUserRoleService;
import com.xinding.travel.util.CodeUtil;

@Controller
@RequestMapping("/admin/customerUser")
public class CustomerUserController extends BaseController {
	
	@Autowired
	private ICustomerUserService customerUserService;
	@Autowired
	private IWhyCustomerUserRoleService customerUserRoleService;
	@Autowired
	private ICustomerAccountService customerAccountService;

	@RequestMapping(value = "/customerUserGrid", method = { RequestMethod.GET })
	@ResponseBody
	public String customerUser(Integer pageNo,Integer pageSize,String name) {
		try {
			PagedResult<CustomerUser> pageResult = customerUserService.customerUserByPage(pageNo, pageSize,name);
			for(CustomerUser cuser:pageResult.getDataList()){
				String timeStr = cuser.getCreateDatetime() == null ? null:cuser.getCreateDatetime().toString();
				cuser.setCreateDatetimeStr(timeStr);
			}
    	    return responseSuccess(pageResult);
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	/**
	 * 用户新增
	 */
	@RequestMapping(value = "/addCustomerUser", method = { RequestMethod.POST })
	@ResponseBody
	@SuppressWarnings("all")
	@Transactional
	public String customerUserAdd(@RequestBody Map p) {
		try {
			String password = (String) p.get("password");
			String name = (String) p.get("nickName");
			String account = (String) p.get("account");
			String tel = null;
			if(p.get("tel") instanceof Long) {
				tel = String.valueOf((Long) p.get("tel"));
			} else {
				tel = String.valueOf((Integer) p.get("tel"));
			}
			Long roleId =  Long.valueOf((String)p.get("roleId"));
			Long customerId = Long.valueOf((String) p.get("customerId"));
			String email = (String) p.get("email");
			
			CustomerUser customerUser = new CustomerUser();
			customerUser.setAccount(account);
			customerUser.setCustomerId(customerId);
			customerUser.setRoleId(roleId);
			customerUser.setName(name);
			customerUser.setTel(tel);
			customerUser.setEmail(email);
			CustomerUser user =  customerUserService.findCustomerUser(customerId, account, roleId);
			if(user != null) {
				return showErrorJson("账号已存在");
			} else {
				Timestamp now = new Timestamp(System.currentTimeMillis());
				customerUser.setCreateDatetime(now);
				customerUser.setLastUpdatedDatetime(now);
				customerUser.setCreateUserId(this.getLoginedUser().getUserId());
				customerUser.setLastUpdatedUserId(this.getLoginedUser().getUserId());
				customerUser.setStatus(new Integer(2));
                int i = customerUserService.AddcustomerUser(customerUser);
                if(i == 1) {
	            	 CustomerAccount customerAccount = new CustomerAccount();
	            	 customerAccount.setCustomerId(customerUser.getCustomerId());
	                 customerAccount.setAccount(customerUser.getAccount()); 
	                 customerAccount.setPassword(CodeUtil.encodePwd(
	                		 customerUser.getAccount(), password));
	                 customerAccount.setStatus(new Integer(2));
	                 customerAccount.setCreateUserId(this.getLoginedUser()
	                         .getUserId());
	                 customerAccount.setLastUpdatedUserId(this.getLoginedUser()
	                         .getUserId());
	                 customerAccount.setCreateDatetime(now);
	                 customerAccount.setLastUpdatedDatetime(now);
	              // 保存用户角色表
	                 WhyCustomerUserRole userRole = new WhyCustomerUserRole();
	                 user =  customerUserService.findCustomerUser(customerId, account, roleId);
	                 userRole.setCustomerUserId(user.getId());
	                 userRole.setRoleId(roleId);
	                 userRole.setStatus(new Integer(2));
	                 userRole.setCreateUserId(this.getLoginedUser().getUserId());
	                 userRole.setLastUpdatedUserId(this.getLoginedUser()
	                         .getUserId());
	                 userRole.setCreateDatetime(now);
	                 userRole.setLastUpdatedDatetime(now);
	                 int k = customerUserRoleService.Addrole(userRole);
	                 int j = customerAccountService.AddcustomerAccount(customerAccount);
	                 if(k == 1 && j == 1) {
	                	 return showSuccessJson("添加成功");
	                 } else {
	                	 return showErrorJson("添加失败");
	                 }
                } else {
                	 return showErrorJson("添加失败");
                }
			}
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/updateCustomerUser", method = { RequestMethod.POST })
	@ResponseBody
	 public String update(@RequestBody Map p) {
		try {
			Long id = Long.valueOf((Integer) p.get("id"));
			Long roleId = Long.valueOf((String) p.get("roleId"));
			String password = (String) p.get("password");
			String oldAccount = "";
            Long cusId=null;
            CustomerUser customerUser = customerUserService.findCustomerUserById(id);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            customerUser.setLastUpdatedDatetime(now);
            customerUser.setLastUpdatedUserId(this.getLoginedUser().getUserId());
            oldAccount = customerUser.getAccount();
            cusId = customerUser.getCustomerId();
            
            String name = (String) p.get("nickName");
			String newAccount = (String) p.get("account");
			String tel = null;
			if(p.get("tel") instanceof Long) {
				tel = String.valueOf((Long) p.get("tel"));
			} else {
				tel = String.valueOf((Integer) p.get("tel"));
			}
			Long customerId = Long.valueOf((String) p.get("customerId"));
			String email = (String) p.get("email");
			
            customerUser.setAccount(newAccount);
            customerUser.setName(name);
            customerUser.setTel(tel);
            customerUser.setEmail(email);
            customerUser.setCustomerId(customerId);
            
            int i = customerUserService.updateCustomerUser(customerUser);
           
            if(i == 1) {
            	Map m = new HashMap();
                m.put("account", oldAccount);
                m.put("customerid", cusId);
            	CustomerAccount customerAccount = customerAccountService.findCustomerAccount(m);
            	logger.info("**********修改用户账号:" + oldAccount);
            	customerAccount.setCustomerId(customerId);
                customerAccount.setAccount(newAccount);
                customerAccount.setPassword(CodeUtil.encodePwd(newAccount, password));
                customerAccount.setLastUpdatedDatetime(now);
                customerAccount.setLastUpdatedUserId(this.getLoginedUser()
                        .getUserId());
                customerAccountService.updateCustomerAccount(customerAccount);
                customerUserRoleService.deleteCustomerUser(id);
                WhyCustomerUserRole customerUserRole = new WhyCustomerUserRole();
                customerUserRole.setCustomerUserId(id);
                customerUserRole.setRoleId(roleId);
                customerUserRole.setStatus(new Integer(2));
                customerUserRole.setCreateUserId(this.getLoginedUser().getUserId());
                customerUserRole.setLastUpdatedUserId(this.getLoginedUser().getUserId());
                customerUserRole.setCreateDatetime(now);
                customerUserRole.setLastUpdatedDatetime(now);
                int k = customerUserRoleService.Addrole(customerUserRole);
                if(k == 1) {
                	return showSuccessJson("添加成功");
                } else {
                	return showErrorJson("添加失败");
                }
                
            } else {
            	 return showErrorJson("添加失败");
            }
		} catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	 }
	
	@RequestMapping(value = "/deleteCustomerUser", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(@RequestBody Map p) {
		try{
			Long id = Long.valueOf((Integer) p.get("id"));
			CustomerUser customerUser = customerUserService.findCustomerUserById(id);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			customerUser.setLastUpdatedDatetime(now);
			customerUser.setStatus(new Integer(0));
			int i = customerUserService.updateCustomerUser(customerUser);
			if(i == 1) {
				Map m = new HashMap();
				m.put("account", customerUser.getAccount());
	            m.put("customerid", customerUser.getCustomerId());
				CustomerAccount customerAccount = customerAccountService.findCustomerAccount(m);
				customerAccount.setStatus(new Integer(0));
				customerAccount.setLastUpdatedDatetime(now);
				int j = customerAccountService.updateCustomerAccount(customerAccount);
				if(j == 1) {
					return showSuccessJson("添加成功");
				} else {
					return showErrorJson("添加失败");
				}
			} else {
				return showErrorJson("添加失败");
			}
		} catch(Exception e) {
			logger.error("*********删除用户发生异常:", e);
			e.printStackTrace();
		}
		return json;
	}
}
