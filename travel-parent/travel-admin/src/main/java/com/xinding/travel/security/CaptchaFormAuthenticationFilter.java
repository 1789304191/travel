package com.xinding.travel.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * @Description: 扩展表单验证过滤器，使之支持验证码
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	public static final String DEFAULT_CUSTOMERID_PARAM = "customer";
    private String captchaParam =DEFAULT_CAPTCHA_PARAM;
    private String customerParam =DEFAULT_CUSTOMERID_PARAM;

    public String getCaptchaParam() {
    	return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
    	return WebUtils.getCleanParam(request, getCaptchaParam());
    }
    
    public String getCustomerParam() {
    	return customerParam;
    }

    protected String getCustomer(ServletRequest request) {
    	return WebUtils.getCleanParam(request, getCustomerParam());
    }

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
      String username = getUsername(request);
      String password = getPassword(request);
      String captcha = getCaptcha(request);
      String customer = getCustomer(request);
      boolean rememberMe = isRememberMe(request);
      String host = getHost(request);

      return new CaptchaUsernamePasswordToken(username, password, rememberMe, host, captcha, customer);
  }

}
