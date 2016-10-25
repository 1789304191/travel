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
import com.xinding.travel.pojo.WHYRegion;
import com.xinding.travel.service.IRegionService;

@Controller
@RequestMapping("/admin/admin-area")
public class RegionController extends BaseController{
	
	@Autowired
	private IRegionService regionService;
	
	@RequestMapping(value = "/areaGrid", method = RequestMethod.GET)
	@ResponseBody
	public String roleGrid(Integer pageNo, Integer pageSize,String name) {
		logger.info("分页查询用户信息列表请求入参：pageNumber{},pageSize{}", pageNo, pageSize);
		try {
			PagedResult<WHYRegion> pageResult = regionService.regionByPage(pageNo, pageSize,name);
			return responseSuccess(pageResult);
		} catch (Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody WHYRegion whyRegion) {
		try {
			whyRegion.setStatus(new Integer(2));
			whyRegion.setCreateUserId(this.getLoginedUser().getUserId());
			whyRegion.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
			regionService.add(whyRegion);
			return showSuccessJson("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return showErrorJson("添加失败");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestBody WHYRegion whyRegion) {
		try {
			Timestamp now=new Timestamp(System.currentTimeMillis());
			whyRegion.setStatus(new Integer(0));
			whyRegion.setLastUpdatedDatetime(now);
			whyRegion.setLastUpdatedUserId(this.getLoginedUser().getUserId());
			regionService.delete(whyRegion);
			return showSuccessJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return showErrorJson("删除失败");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestBody WHYRegion whyRegion) {
		try {
			Timestamp now=new Timestamp(System.currentTimeMillis());
			whyRegion.setLastUpdatedDatetime(now);
			whyRegion.setLastUpdatedUserId(this.getLoginedUser().getUserId());
			regionService.update(whyRegion);
			return showSuccessJson("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return showErrorJson("修改失败");
	}
	
	@RequestMapping(value = "/regionSelectList", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("all")
	public List<Map> regionSelectList() {
		try {
			return regionService.regionSelectList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
