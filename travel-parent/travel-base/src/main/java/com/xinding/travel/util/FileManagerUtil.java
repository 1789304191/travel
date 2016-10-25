/**   
 * @Title: FileManagerUtil.java 
 * @Package com.wonders.why.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhoudezhang Zhou
 * @date 2015-3-19 下午2:00:51 
 * @version V1.0   
 */
package com.xinding.travel.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @ClassName: FileManagerUtil
 * @Description: 解析文件路径配置文件
 * @author A18ccms a18ccms_gmail_com
 * @date 2015-3-19 下午2:00:51
 * 
 */
public class FileManagerUtil {
	private static Properties p = new Properties();
	private static Resource fileRource = new ClassPathResource("sys_cfg.properties");
	static{
		InputStream in;
		try {
			in = fileRource.getInputStream();
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static String getFileMainPath() {
    	return p.getProperty("FILE_MAIN_PATH");
    }

    // 获取显示照片URL
    public static String getDoMainPath() {
    	return p.getProperty("DOMAIN_URL");
    }

    /**
     * 
     * @description 配置视频路径 <br/>
     * 
     * @return String
     * @throws
     */
    public static String getVideoPath() {
    	return p.getProperty("VIDEO_FILE_URL");
    }


	// 获取各个模块图片
	public static String getPicturePath(String fileName, String model) {
		String picpath = "";
		if ("event".equals(model)) {
			picpath = p.getProperty("EVENT_PICTURE_URL") + getYYYYMMDD_Dirs(fileName) + "/";
		} else if ("spot".equals(model)) {
			picpath = p.getProperty("SPOT_PICTURE_URL") + getYYYYMMDD_Dirs(fileName) + "/";
		} else if ("venues".equals(model)) {
			picpath = p.getProperty("VENUES_PICTURE_URL") + getYYYYMMDD_Dirs(fileName) + "/";
		}else if ("qrcode".equals(model)) {
			picpath = p.getProperty("QCODE_PICTURE_URL") + getYYYYMMDD_Dirs(fileName) + "/";
		}else if ("pda".equals(model)) {
			picpath = p.getProperty("PDA_PICTURE_URL") + getYYYYMMDD_Dirs(fileName) + "/";
		}
		return picpath;
	}


    public static String getFullFilePath(String fileName, String from,
            String type) throws Exception {
        String filePath = "";
        filePath = type + "/" + from + "/" + getYYYYMMDD_Dirs(fileName) + "/"
                + fileName;
        return filePath;
    }

    public static String getFileFullName(String fileName, String path,
            String from) throws Exception {
        String fileFullName = "";
        fileFullName = getFileMainPath() + "/" + path + "/" + from + "/"
                + getYYYYMMDD_Dirs(fileName) + "/" + fileName;
        return fileFullName;
    }

    public static String getFilePath(String fileName, String path, String from)
            throws Exception {
        String filePath = "";
        filePath = getFileMainPath() + "/" + path + "/" + from + "/"
                + getYYYYMMDD_Dirs(fileName) + "/";
        return filePath;
    }

    public static String getFilename(String originalFilename) throws Exception {
        String fileName = "";
        UUID uuid = UUID.randomUUID();
        fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-"
                + uuid.toString() + getFileExt(originalFilename);
        return fileName;
    }

    public static String getFileExt(String fileName) throws IOException {
        if ((null == fileName) || ("".equals(fileName.trim()))) {
            throw new IOException("file name can not be empty");
        }
        if (fileName.lastIndexOf(".") < 1) {
            throw new IOException("can not found file extension");
        }
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return extension;
    }

    // 根据文件名获取文件相对目录 文件名:2015-03-24-4ae4265c-ee20-4797-8df6-3e4b4ecd0aa8.jpg
    public static String getYYYYMMDD_Dirs(String fileName) {
        String[] ymdDirs = fileName.split("-");
        return ymdDirs[0] + "/" + ymdDirs[1] + "/" + ymdDirs[2];
    }

	// 获取音频
	public static String getMusicPath(String fileName, String model) {
		String picpath = "";
		if ("spot".equals(model)) {
			picpath = p.getProperty("SPOT_MUSIC_URL") + getYYYYMMDD_Dirs(fileName) + "/";
		}
		return picpath;
	}
    
   
}
