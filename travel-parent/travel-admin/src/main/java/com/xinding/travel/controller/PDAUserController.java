package com.xinding.travel.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.PDAUser;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IPDAUserService;

@Controller
@RequestMapping("/pdauser")
public class PDAUserController extends BaseController {
	
	@Autowired
	private IPDAUserService pdaUserService;

	@RequestMapping(value = "/pdaUserGrid", method = { RequestMethod.POST })
	@ResponseBody
	public String pdaUserList(@RequestBody Map p) {
		try {
			PagedResult<PDAUser> pageResult = pdaUserService.pdaUserByPage(p);
			for(PDAUser pdaUser:pageResult.getDataList()){
				String timeStr = pdaUser.getCreateDatetime() == null ? null:pdaUser.getCreateDatetime().toString();
				pdaUser.setCreateDatetimeStr(timeStr);
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
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	@ResponseBody
	@SuppressWarnings("all")
	@Transactional
	public String add(@RequestBody Map p) {
		try {
			String account = (String)p.get("account");
			String password = (String)p.get("password");
			String name = (String)p.get("name");
			String firstPic = (String)p.get("firstPic");
			PDAUser pdaUser = pdaUserService.findByAccount(account);
			if(pdaUser == null) {
				PDAUser user = new PDAUser();
				user.setAccount(account);
				user.setPassword(password);
				user.setFirstPic(firstPic);
				user.setName(name);
				Timestamp now = new Timestamp(System.currentTimeMillis());
				user.setCreateDatetime(now);
				user.setLastUpdatedDatetime(now);
				user.setCreateUserId(this.getLoginedUser().getUserId());
				user.setLastUpdatedUserId(this.getLoginedUser().getUserId());
				user.setStatus(new Integer(2));
				int i = pdaUserService.add(user);
				if(i == 1) {
					pdaUser = pdaUserService.findByAccount(account);
					String employeeNo = pdaUser.getId().toString();
					for(int k=8 ; k >= employeeNo.length() ; k--) {
						employeeNo = "0" + employeeNo;
					}
					pdaUserService.addEmployeeNo(employeeNo, pdaUser.getAccount());
					return showSuccessJson("添加成功");
				} else {
					return showErrorJson("添加失败");
				}
			} else {
				return showErrorJson("用户重复");
			}
		} catch(Exception e) {
			e.printStackTrace();
			return showErrorJson("添加失败");
		}
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	 public String update(@RequestBody Map p) {
		try {
			Long id = Long.valueOf((Integer) p.get("id"));
			String account = (String)p.get("account");
			String password = (String)p.get("password");
			String name = (String)p.get("name");
			String firstPic = (String)p.get("firstPic");
			PDAUser pdaUser = new PDAUser();
			pdaUser.setAccount(account);
			pdaUser.setPassword(password);
			pdaUser.setName(name);
			pdaUser.setFirstPic(firstPic);
			pdaUser.setId(id);
			pdaUser.setStatus(new Integer(2));
			Timestamp now = new Timestamp(System.currentTimeMillis());
			pdaUser.setLastUpdatedDatetime(now);
			pdaUser.setLastUpdatedUserId(this.getLoginedUser().getUserId());
			int i = pdaUserService.update(pdaUser);
			if (i == 1) {
				return showSuccessJson("添加成功");
			} else {
				return showErrorJson("添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return showErrorJson("添加失败");
		}
	}
	
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(@RequestBody Map p) {
		Long id = Long.valueOf((Integer) p.get("id"));
		pdaUserService.delete(id);
		return showSuccessJson("删除成功");
	}
	
	/**
	 * PDA用户与项目绑定
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/bind", method = { RequestMethod.POST })	
	@ResponseBody
	public String bindProject(@RequestBody Map p) {
		List<Integer> eventIds = (List<Integer>) p.get("eventId");
		if(eventIds.size() > 0) {
			Long[] scenicId = new Long[eventIds.size()];
			for(int j=0 ; j<eventIds.size() ; j++) {
				scenicId[j] = Long.valueOf(eventIds.get(j));
			}
			Long pdaUserId = Long.valueOf((Integer)p.get("pdaUserId"));
		    pdaUserService.deleteProjectPDAuserId(pdaUserId);
		    for(int k=0 ; k < scenicId.length ; k++) {
		    	pdaUserService.bind(scenicId[k], pdaUserId);
		    }
	    	return showSuccessJson("绑定成功");
			
		} else {
			Long pdaUserId = Long.valueOf((Integer)p.get("pdaUserId"));
			pdaUserService.deleteProjectPDAuserId(pdaUserId);
			return showSuccessJson("全部删除");
		}
	}
}
