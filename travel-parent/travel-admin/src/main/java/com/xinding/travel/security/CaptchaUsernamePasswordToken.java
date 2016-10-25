package com.xinding.travel.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description: 扩展shiro用户密码口令类，使之支持验证码
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
	 private static final long serialVersionUID = 1L;
     private String captcha;
     private String customer;
     private boolean useCaptcha = true;

     public String getCaptcha() {
          return captcha;
     }

     public void setCaptcha(String captcha) {
          this.captcha = captcha;
     }

     public boolean isUseCaptcha() {
		return useCaptcha;
	 }

	 public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	 }

     public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public CaptchaUsernamePasswordToken() {
          super();
     }

     public CaptchaUsernamePasswordToken(String username,String password, boolean rememberMe, String host,String captcha,String customer) {        
          super(username, password, rememberMe, host);
          this.captcha = captcha;
          this.customer = customer;
     }
}
