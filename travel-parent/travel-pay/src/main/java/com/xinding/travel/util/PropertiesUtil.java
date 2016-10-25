package com.xinding.travel.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.xinding.travel.vo.PropertiesVo;

public class PropertiesUtil {
	
	public static PropertiesVo getProperties(){
		/** 读取配置文件获取支付宝参数 */
		PropertiesVo po = new PropertiesVo();
		Resource fileRource = new ClassPathResource("sys_cfg.properties");
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = fileRource.getInputStream();
			prop.load(in);
			// 初始化支付宝配置参数
			String orderIdentify = prop.getProperty("ORDER_IDENTIFY");
			String alipay_notify_url = prop.getProperty("alipay_notify_url");
			String wxpay_notify_url = prop.getProperty("wxpay_notify_url");
			po.setOrderIdentify(orderIdentify);
			po.setAlipay_notify_url(alipay_notify_url);
			po.setWxpay_notify_url(wxpay_notify_url);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return po;
	}
}
