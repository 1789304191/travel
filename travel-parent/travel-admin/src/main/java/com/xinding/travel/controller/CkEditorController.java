package com.xinding.travel.controller;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xinding.travel.constant.SystemConstant;
import com.xinding.travel.util.FileManagerUtil;

@Controller  
@RequestMapping(value="/ckEditor")
public class CkEditorController extends BaseController{
	
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	@ResponseBody
	@SuppressWarnings("all")
	public String ckUpload(MultipartFile upload,String CKEditorFuncNum,String from,HttpServletResponse response ) {
		try {
			String fileTypePath = SystemConstant.IMG_PATH;
			String fileName = FileManagerUtil.getFilename(upload.getOriginalFilename());
			String uploadContentType=upload.getContentType();
			File uploadPath = new File(FileManagerUtil.getFilePath(fileName, fileTypePath,from));
			if (!uploadPath.exists()) {
				uploadPath.mkdirs();// 目录不存在的情况下，创建目录。
			}
			upload.transferTo(new File(FileManagerUtil.getFileFullName(fileName, fileTypePath,from)));
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			 if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {  
		            // IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg  
		        } else if (uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")) {  
		            // IE6上传的png图片的headimageContentType是"image/x-png"  
		        } else if (uploadContentType.equals("image/gif")) {  
		        } else if (uploadContentType.equals("image/bmp")) {  
		        } else {  
		            out.println("<script type=\"text/javascript\">");  
		            out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum  
		                    + ",'','文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");  
		            out.println("</script>");  
		            return null;  
		        }  
			if (upload.getSize() > SystemConstant.IMG_LIMIT_SIZE) {  
		            out.println("<script type=\"text/javascript\">");  
		            out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum  
		                    + ",''," + "'文件大小不得大于500k');");  
		            out.println("</script>");  
		            return null;  
		    }  
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'"
					+ FileManagerUtil.getPicturePath(fileName, from) + fileName + "','')");
			out.println("</script>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
