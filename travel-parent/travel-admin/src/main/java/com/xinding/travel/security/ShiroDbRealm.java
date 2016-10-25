package com.xinding.travel.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.kaptcha.Constants;
import com.xinding.travel.pojo.CustomerAccount;
import com.xinding.travel.pojo.CustomerUser;
import com.xinding.travel.pojo.SessionUser;
import com.xinding.travel.pojo.WHYCustomer;
import com.xinding.travel.pojo.WhyCustomerUserRole;
import com.xinding.travel.pojo.WhyPrivilege;
import com.xinding.travel.pojo.WhyRole;
import com.xinding.travel.service.ICustomerAccountService;
import com.xinding.travel.service.ICustomerService;
import com.xinding.travel.service.ICustomerUserService;
import com.xinding.travel.service.IWhyCustomerUserRoleService;
import com.xinding.travel.service.IWhyPrivilegeService;
import com.xinding.travel.service.IWhyRoleService;

/**
 * @Description: shiro数据库认证授权类
 */
public class ShiroDbRealm extends AuthorizingRealm {

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private ICustomerAccountService customerAccountService;

	@Autowired
	private ICustomerUserService customerUserService;

	@Autowired
	private IWhyCustomerUserRoleService whyCustomerUserRoleService;

	@Autowired
	private IWhyRoleService whyRoleService;
	
	@Autowired
	private IWhyPrivilegeService whyPrivilegeService;

	/**
	 * <p>
	 * Title: doGetAuthenticationInfo
	 * </p>
	 * <p>
	 * Description: 认证回调函数，登陆时调用
	 * </p>
	 * 
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	@SuppressWarnings("all")
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
		String accountName = token.getUsername();
		String customerid = token.getCustomer();
		WHYCustomer cus = null;
		// 驻户验证
		if (StringUtils.isBlank(customerid)) {
			throw new UnknownCustomerException("Unkown Customer.");
		} else {
			List<WHYCustomer> list = customerService.selectCustomer(null);
			for (WHYCustomer c : list) {
				if (c.getId().equals(Long.valueOf(customerid))) {
					cus = c;
					break;
				}
			}
			if (cus == null) {
				throw new UnknownCustomerException("Unkown Customer.");
			}
		}
		// 验证码 验证
		if (token.isUseCaptcha()) {
			String captcha = null;
			Object obj_captcha = SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (obj_captcha instanceof String)
				captcha = (String) obj_captcha;
			if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
				throw new IncorrectCaptchaException("Captcha is incorrect.");
			}
		}
		CustomerAccount account = null;
		Map p = new HashMap();
		p.put("account", accountName);
		p.put("customerid", customerid);
		List<CustomerAccount> list = customerAccountService.selectCustomerAccount(p);
		CustomerUser user = customerUserService.findCustomerUser(Long.valueOf(customerid), accountName, null);
		if (list != null && list.size() != 0) {
			account = list.get(0);
		}
		if (account != null) {
			// 判断账号是否已删除或锁定
			if (account.getStatus().intValue() == 1 || account.getStatus().intValue() == 0) {
				throw new LockedAccountException("Account is deleted or locked.");
			}
			// 把需要放在session的用户数据封装起来
			SessionUser sessionUser = new SessionUser();
			sessionUser.setUserId(user.getId());
			sessionUser.setAccount(account.getAccount());
			sessionUser.setPassword(account.getPassword());
			sessionUser.setUserName(account.getNickName());
			sessionUser.setUserEmail(account.getEmail());
			sessionUser.setUserTel(account.getTel());
			sessionUser.setCustomerId(cus.getId());
			sessionUser.setCustomerName(cus.getName());

			return new SimpleAuthenticationInfo(sessionUser, account.getPassword(), getName());
		} else {
			throw new UnknownAccountException("No account found for user [" + accountName + "]");
		}
	}

	/**
	 * <p>
	 * Title: doGetAuthorizationInfo
	 * </p>
	 * <p>
	 * Description: 授权查询回调函数 ，进行鉴权但缓存中无用户的授权信息时调用
	 * </p>
	 * 
	 * @param arg0
	 * @return
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	@SuppressWarnings("all")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SessionUser sessionUser = (SessionUser) principals.fromRealm(getName()).iterator().next();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 分2种情况：
		// 1、超级管理员用户所有权限
		if (sessionUser.getUserId() == 1) {
			info.addStringPermission("*");
		}
		// 2、其他人员由超级管理员分配权限，从权限组获取权限列表
		else {
			try {
				List<String> roleIds = new ArrayList<String>();
				Map p = new HashMap();
				p.put("customerUserId", sessionUser.getUserId());
				List<WhyCustomerUserRole> user = whyCustomerUserRoleService.customerUserRoleList(p);
				for (WhyCustomerUserRole userRole : user) {
					roleIds.add(userRole.getRoleId().toString());
				}
				if (roleIds.size() > 0) {
					List<String> privilegesIds = new ArrayList<String>();
					String param = StringUtils.join(roleIds, ",");
					Map rId = new HashMap();
					rId.put("id", param);
					List<WhyRole> roles = whyRoleService.findRoleById(rId);
					for (WhyRole role : roles) {
						String privilegeCodes = role.getPrivilegeCodes();
						if (StringUtils.isNotBlank(privilegeCodes)) {
							String[] arrIds = StringUtils.split(privilegeCodes, ",");
							for (String id : arrIds) {
								privilegesIds.add(id);
							}
						}
					}
					if (privilegesIds.size() > 0) {
						Map ps=new HashMap();
	                	ps.put("id",privilegesIds);
                        List<WhyPrivilege> privilegeList = whyPrivilegeService.privilegesThird(ps);
                        for (WhyPrivilege privilege : privilegeList) {
                            info.addStringPermission(privilege.getSref());
                        }
                    }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
}
