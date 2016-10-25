/**   
* @Title: UID.java 
* @Package com.wonders.why.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoudezhang Zhou
* @date 2015-4-14 上午11:23:52 
* @version V1.0   
*/
package com.xinding.travel.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UID
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoudezhang
 * @date 2015-4-14 上午11:23:52
 * 
 */
public class GenerateCode {
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 99999;

	public static synchronized long next() {
		if (seq > ROTATION)
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);
		return Long.parseLong(str);
	}

//	public static String getZiRanExtendCode(int scheduleId, int type, int userId, int num) throws Exception {
//		String code = "";
//		HttpRequestClient client = new HttpRequestClient();
//		String param = "scheduleId=" + scheduleId + "&type=" + type + "&userId=" + userId + "&num=" + num;
//		String result = client.sendPost("http://211.144.107.201:9090/museum/app/museum/book/preorder", param);
//		System.out.println("***************自然返回预约结果:" + result);
//		Map<String, Object> obj = null;
//		obj = JacksonUtil.getInstance().readValue(result, Map.class);
//		if (obj != null) {
//			if (obj.get("result") != null) {
//				code = obj.get("result").toString();
//			}
//		}
//		return code;
//	}

	/**
	 * 格式化时间
	 * 
	 * @return
	 */
	public static String dateToString() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String dateString = sf.format(date);
		return dateString;
	}

	/**
	 * 生成随机数 dongjun
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String randomNumber(int start, int end) {
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
				"13", "14", "15", "16", "17", "18", "19", "20" };
		List list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(start, end);
		return result;
	}

	public static void main(String[] args) {

//		System.out.println(dateToString());
	}
}
