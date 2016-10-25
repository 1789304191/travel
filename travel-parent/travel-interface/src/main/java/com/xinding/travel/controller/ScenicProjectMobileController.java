package com.xinding.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.PropertiesVo;
import com.xinding.travel.pojo.ScenicProject;
import com.xinding.travel.service.IScenicProjectMobileService;
import com.xinding.travel.util.FileManagerUtil;
import com.xinding.travel.util.PropertiesObtain;

@Controller
@RequestMapping("/scenicProjectMobile")
public class ScenicProjectMobileController  extends BaseController{

	@Autowired
	private IScenicProjectMobileService iScenicProjectMobileService;
	
	@RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public String list(Integer pageNo,Integer pageSize,String projectCode) {
		try {
			PagedResult<ScenicProject> pageResult = iScenicProjectMobileService.scenicProjectByPage(pageNo, pageSize,projectCode);
			PropertiesVo po = PropertiesObtain.getProperties();
			for(ScenicProject sp:pageResult.getDataList()){
    	    	sp.setFirstPic(po.getSpot_picture_url()+FileManagerUtil.getYYYYMMDD_Dirs(sp.getFirstPic()) + "/"+ sp.getFirstPic());
    	    }
			return responseSuccess(pageResult);
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
    }
	
	@RequestMapping(value="/detail", method= RequestMethod.GET)
    @ResponseBody
	public ScenicProject detail(Integer id){
		PropertiesVo po = PropertiesObtain.getProperties();
		ScenicProject sp = iScenicProjectMobileService.detail(id);
		sp.setFirstPic(po.getSpot_picture_url()+FileManagerUtil.getYYYYMMDD_Dirs(sp.getFirstPic()) + "/"+ sp.getFirstPic());
		return sp;
	}
}
