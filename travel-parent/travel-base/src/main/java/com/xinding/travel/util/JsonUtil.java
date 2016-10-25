package com.xinding.travel.util;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * TODO(Json工具类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:235862,date:2015-11-20 下午4:01:52,content:TODO
 * </p>
 * 
 * @author 235862
 * @date 2015-11-20 下午4:01:52
 * @since
 * @version
 */
public class JsonUtil {

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author 235862
	 * @date 2015-11-20 下午4:12:10
	 * @return
	 * @see
	 */
	public static Object jsonToValue(String arg, String key1, String key2) {
		/**
		 * 最终需要的object
		 */
		Object needObject = null;
		/**
		 * JSONArray
		 */
		JSONArray jsonArray = null;
		/**
		 * String转JSONArray
		 */
		jsonArray = new JSONArray(arg);
		/**
		 * needStr
		 */
		String needStr = "";
		/**
		 * key1:第一层
		 */
		if (StringUtils.isNotBlank(key1)) {
			/**
			 * 第一层值
			 */
			needStr = jsonArray.getJSONObject(0).get(key1).toString();
			/**
			 * 输出第一层
			 */
			// System.out.println("----------"+needStr);
			/**
			 * 最终object
			 */
			needObject = needStr;
		}
		/**
		 * key2:第二层，如果只有一层json则key2为null
		 */
		if (StringUtils.isNotBlank(key2)) {
			/**
			 * 转成obj
			 */
			JSONObject obj = new JSONObject(needStr);
			/**
			 * 输出第二层
			 */
			// System.out.println("----------"+obj.get(key2));
			/**
			 * 获得结果（第二层值）
			 */
			needObject = obj.get(key2);
		}

		return needObject;
	}
}
