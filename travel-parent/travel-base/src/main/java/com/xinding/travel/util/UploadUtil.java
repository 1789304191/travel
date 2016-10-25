package com.xinding.travel.util;

import java.io.File;

import com.xinding.travel.constant.SystemConstant;

public class UploadUtil {

	/**
	 * 
	 * <p>
	 * TODO(二维码上传)
	 * </p>
	 * 
	 * @author dongjun
	 * @date 2016年4月14日 下午9:34:25
	 * @param text
	 * @return
	 * @see
	 */
	public static String doQrcodeUpload(String text) {
		String fileName = "qrcode.jpg";
		try {
			String fileTypePath = SystemConstant.IMG_PATH;
			// 确定上传来之哪个模块
			String fromName = SystemConstant.QRCODE_PATH;
			fileName = FileManagerUtil.getFilename(fileName);
			File uploadPath = new File(FileManagerUtil.getFilePath(fileName, fileTypePath, fromName));
			if (!uploadPath.exists()) {
				uploadPath.mkdirs();// 目录不存在的情况下，创建目录。
			}

			String destPath = FileManagerUtil.getFilePath(fileName, fileTypePath, fromName);
			QRCodeUtil.encode(text, null, destPath, fileName, true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileName;
	}

	public static void main(String[] args) {
		// 短信验证码
		String code = GenerateCode.randomNumber(1, 7);
		// 二维码字符串
		String qrcode_str = String.valueOf("http://m.pachongshe.com/d.html") + "?sign_code=" + code;
		// 保存二维码并获取二维码文件名称
		String fileName = UploadUtil.doQrcodeUpload(qrcode_str);
		System.out.println(fileName);
	}
}
