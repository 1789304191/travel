package com.xinding.travel.controller;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.PDADevice;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IPDADeviceService;

@Controller
@RequestMapping("/pdadevice")
public class PDADeviceController extends BaseController {
	
	@Autowired
	private IPDADeviceService pdaDeviceService;

	@RequestMapping(value = "/pdaDeviceGrid", method = { RequestMethod.GET })
	@ResponseBody
	public String pdaUserList(Integer pageNo,Integer pageSize) {
		try {
			PagedResult<PDADevice> pageResult = pdaDeviceService.pdaUserByPage(pageNo, pageSize);
			for(PDADevice pdaDevice:pageResult.getDataList()){
				String timeStr = pdaDevice.getCreateDatetime() == null ? null:pdaDevice.getCreateDatetime().toString();
				pdaDevice.setCreateDatetimeStr(timeStr);
			}
    	    return responseSuccess(pageResult);
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	@ResponseBody
	@SuppressWarnings("all")
	@Transactional
	public String add(@RequestBody Map p) {
		try {
			String deviceNo = (String)p.get("deviceNo");
			PDADevice pdaDevice = new PDADevice();
			pdaDevice.setDeviceNo(deviceNo);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			pdaDevice.setCreateDatetime(now);
			pdaDevice.setLastUpdatedDatetime(now);
			pdaDevice.setStatus(new Integer(2));
			int i = pdaDeviceService.add(pdaDevice);
			if(i == 1) {
				return showSuccessJson("添加成功");
			} else {
				return showErrorJson("添加失败");
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
			String deviceNo = (String)p.get("deviceNo");
			Long id = Long.valueOf((Integer) p.get("id"));
			PDADevice pdaDevice = new PDADevice();
			pdaDevice.setId(id);
			pdaDevice.setDeviceNo(deviceNo);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			pdaDevice.setLastUpdatedDatetime(now);
			int i = pdaDeviceService.update(pdaDevice);
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
		try {
			Long id = Long.valueOf((Integer) p.get("id"));
			int i = pdaDeviceService.delete(id);
			if(i == 1) {
				return showSuccessJson("删除成功");
			} else {
				return showErrorJson("删除失败");
			}
		} catch(Exception e) {
			e.printStackTrace();
			return showErrorJson("删除失败");
		}
		
	}
}
