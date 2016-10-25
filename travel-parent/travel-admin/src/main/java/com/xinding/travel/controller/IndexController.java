/**   
 * @Title: LoginController.java 
 * @Package com.wonders.why.controller 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhoudezhang Zhou
 * @date 2015-3-17 上午12:50:50 
 * @version V1.0   
 */
package com.xinding.travel.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.pojo.CustomerAccount;
import com.xinding.travel.pojo.SessionUser;
import com.xinding.travel.security.CaptchaUsernamePasswordToken;
import com.xinding.travel.security.IncorrectCaptchaException;
import com.xinding.travel.security.UnknownCustomerException;
import com.xinding.travel.service.ICustomerService;
import com.xinding.travel.util.CodeUtil;

/**
 * @ClassName: IndexController
 * @Description: 登陆页面
 * @date 2015-3-17 上午12:50:50
 * 
 */
@Controller  
@RequestMapping("/index")
public class IndexController extends BaseController {
	
	@Autowired
	private ICustomerService customerService;
	
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	@SuppressWarnings("all")
    public  String login() throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        Map p=new HashMap();
        if (currentUser != null && currentUser.isAuthenticated()) {
            // 是万达用户
//             if (this.getLoginedUser().getCustomerId() == 1) {
//            redirect("/main");
//             } else {
//             // 不是万达用户
//             redirect("/reservation/index?customerId="
//             + this.getLoginedUser().getCustomerId());
//             }
        	p.put("isLogin",true);
        	return responseSuccess(p);

        } else {
        	p.put("isLogin",false);
        	p.put("array", customerService.selectCustomer(null));
        	return responseSuccess(p);
        }
    }

    /**
     * 
     * @description queryLogin <br/>
     * 
     * @throws Exception
     *             void
     * @throws
     */
	@RequestMapping(value = "/queryLogin", method = { RequestMethod.POST })
	@ResponseBody
	@SuppressWarnings("all")
    public String queryLogin() throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        Map p=new HashMap();
        if (currentUser != null && currentUser.isAuthenticated()) {
        	p.put("isLogin",true);
            return responseSuccess(p);
        } else {
        	p.put("isLogin",false);
        	return responseSuccess(p);
        }
    }
    
    @RequestMapping(value = "/doLogin", method = { RequestMethod.POST })
    @ResponseBody
    public String doLogin(HttpServletRequest request,@RequestBody  CustomerAccount customer) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken();
            token.setUsername(customer.getAccount());
            token.setPassword(CodeUtil.encodePwd(
            		customer.getAccount(),
                    customer.getPassword()).toCharArray());
            //token.setCaptcha(request.getParameter("captcha"));
            token.setCustomer(customer.getCustomerId().toString());
            token.setRememberMe(true);
            token.setHost(request.getRemoteHost());
            // 启用验证码
            //token.setUseCaptcha(true);
            token.setUseCaptcha(false);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            // // 判断驻户是否是万达信息
            // System.out.println("*****" + request.getParameter("customer"));
            // if (request.getParameter("customer").equals("1")) {
            // // 是万达
            // result.put("isWonders", "0");
            // result.put("code", 0);
            // result.put("msg", "登陆成功!");
            // } else {
            // // 不是万达
            // result.put("isWonders", "-1");
            // result.put("code", 0);
            // result.put("msg", "登陆成功!");
            // }
            result.put("code", 0);
            result.put("msg", "登陆成功!");
         // 剔除其他此账号在其它地方登录
			List<Session> loginedList = getLoginedSession(currentUser);
			for (Session session : loginedList) {
				 session.stop();
			}
        } catch (UnknownCustomerException uae) {
            result.put("code", -1);
            result.put("msg", "驻户不存在!");
        } catch (UnknownAccountException uae) {
            result.put("code", -1);
            result.put("msg", "用户不存在!");
        } catch (IncorrectCredentialsException ice) {
            result.put("code", -1);
            result.put("msg", "密码不正确!");
        } catch (IncorrectCaptchaException ice) {
            result.put("code", -1);
            result.put("msg", "验证码不正确!");
        } catch (LockedAccountException lae) {
            result.put("code", -1);
            result.put("msg", "账户被删除或禁用!");
        } catch (AuthenticationException ae) {
            ae.printStackTrace();
            result.put("code", -1);
            result.put("msg", "登陆失败!");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "系统错误!");
        }
        return responseSuccess(result);
    }

    @RequestMapping(value = "/signout")
    public void signout() throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null && currentUser.isAuthenticated()) {
            currentUser.logout();
        }
    }

    public void noPermisson() throws Exception {
//        String id = (String) this.request.getParameter("id");
//        String msg = null;
//        if (id != null && !id.equals("-1")) {
//            WHYPrivileges privileges = WHYPrivileges.find(WHYPrivileges.class,
//                    id);
//            msg = privileges.getName();
//        } else {
//            msg = "该权限未录入";
//        }
//
//        request.setAttribute("msg", msg);

    }

    public void error() throws Exception {

    }
    private List<Session> getLoginedSession(Subject currentUser) {
		Collection<Session> list = ((DefaultSessionManager) ((DefaultSecurityManager) SecurityUtils
				.getSecurityManager()).getSessionManager()).getSessionDAO()
				.getActiveSessions();
		List<Session> loginedList = new ArrayList<Session>();
		SessionUser loginUser = (SessionUser) currentUser.getPrincipal();
		for (Session session : list) {

			Subject s = new Subject.Builder().session(session).buildSubject();

			if (s.isAuthenticated()) {
				SessionUser user = (SessionUser) s.getPrincipal();
				if (user.getAccount().equalsIgnoreCase(loginUser.getAccount())) {
					if (!session.getId().equals(
							currentUser.getSession().getId())) {
						loginedList.add(session);
					}
				}
			}
		}
		return loginedList;
	}
}
