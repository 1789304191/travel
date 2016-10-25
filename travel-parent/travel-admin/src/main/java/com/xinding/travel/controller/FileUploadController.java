package com.xinding.travel.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xinding.travel.constant.SystemConstant;
import com.xinding.travel.util.FileManagerUtil;

@Controller  
@RequestMapping("/file")
public class FileUploadController extends BaseController{
	
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	@ResponseBody
	@SuppressWarnings("all")
	public String upload(MultipartFile file,String from){
//		ServletContext context = req.getSession().getServletContext();
//		System.out.println(context.getRealPath("/") );
		String fileTypePath = SystemConstant.IMG_PATH;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("******************上传文件名称:" + file.getOriginalFilename());
			String fileName = FileManagerUtil.getFilename(file.getOriginalFilename());
			logger.info("**************生成文件名:" + fileName);	
			File uploadPath = new File(FileManagerUtil.getFilePath(fileName, fileTypePath, from));
			if (!uploadPath.exists()) {
				uploadPath.mkdirs();// 目录不存在的情况下，创建目录。
			}
			file.transferTo(new File(FileManagerUtil.getFileFullName(fileName, fileTypePath, from)));
			result.put("fileName", fileName);
			result.put("path",FileManagerUtil.getPicturePath(fileName, from) + fileName);
			return responseSuccess(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return showErrorJson("上传失败");
		
	}
	
	/**
	 * 获取图片路径
	 * @param name
	 * @param from
	 * @return
	 */
	@RequestMapping(value = "/getPic", method = { RequestMethod.POST })
	@ResponseBody
	@SuppressWarnings("all")
	public String getPic(String firstPic,String from){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("path",FileManagerUtil.getPicturePath(firstPic, from) + firstPic);
		return responseSuccess(result);
	}
	
	
}
