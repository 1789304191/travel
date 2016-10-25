package com.xinding.travel.weixin.util;

import org.apache.log4j.Logger;

import com.xinding.travel.util.HttpRequestClient;
import com.xinding.travel.util.JsonUtil;
import com.xinding.travel.weixin.common.Configure;
import com.xinding.travel.weixin.common.WxConstant;

public class WeixinUtil {
	
	private static Logger log = Logger.getLogger(WeixinUtil.class);

	public static String getWeixinOpenId(String code) {

		log.info("--- 获取特殊的网页授权access_token ---");
		StringBuffer url = new StringBuffer(WxConstant.WX_ACCESS_TOKEN);
		url.append("&appid=");
		url.append(Configure.getAppid());
		url.append("&secret=");
		url.append(WxConstant.WX_APPSECRET);
		url.append("&code=");
		url.append(code);
		log.info("--- code ---" + code);
		// 获取access_token
		String jsonString = "[" + HttpRequestClient.sendGet(url.toString(), null) + "]";
		// 获取openid
		String openid = JsonUtil.jsonToValue(jsonString, "openid", null).toString();
		log.info("--- 获取微信用户openid ---" + openid);
		return openid;
	}

}
