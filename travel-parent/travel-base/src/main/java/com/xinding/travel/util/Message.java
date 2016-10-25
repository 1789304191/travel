package com.xinding.travel.util;

import java.util.Map;

public class Message {
	// 请求标识 true/false
	private boolean requestFlag;
	// 相应返回数据
	private Map responseEntiy;
	// 错误信息
	private String messsage;
	// 代码值
	private Integer code;

	/**
	 * @return the requestFlag
	 */
	public boolean isRequestFlag() {
		return requestFlag;
	}

	/**
	 * @param requestFlag
	 *            the requestFlag to set
	 */
	public void setRequestFlag(boolean requestFlag) {
		this.requestFlag = requestFlag;
	}

	/**
	 * @return the responseEntiy
	 */
	public Map getResponseEntiy() {
		return responseEntiy;
	}

	/**
	 * @param responseEntiy
	 *            the responseEntiy to set
	 */
	public void setResponseEntiy(Map responseEntiy) {
		this.responseEntiy = responseEntiy;
	}

	public String getMesssage() {
		return messsage;
	}

	public void setMesssage(String messsage) {
		this.messsage = messsage;
	}

	/**
	  * @return  the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

}
