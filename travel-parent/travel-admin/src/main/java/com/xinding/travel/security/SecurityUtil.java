package com.xinding.travel.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.xinding.travel.pojo.SessionUser;

public class SecurityUtil {
	public static boolean checkPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ref = request.getHeader("Referer");
		if (ref == null) {
			String uri = request.getRequestURI();
			if (!uri.equals(request.getContextPath() + "/")) {
				// LogUtil.log.error("Invalid URL Access:referer=" + ref +
				// ",url="
				// + uri + "?" + request.getQueryString());

				StringBuilder js = new StringBuilder("<script>");
				js.append("alert('Invalid manual URL Accessing!');");
				js.append("</script>");

				PrintWriter out = response.getWriter();
				out.println(js);
				out.flush();
				out.close();
				return false;
			}
		}
		return true;
	}

	public static void close(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder js = new StringBuilder("<script>");
		js.append("var win=window;");
		js.append("while(win.parent!=window.top){win=win.parent;}");
		js.append("win.parent.close();");
		js.append("</script>");

		PrintWriter out = response.getWriter();
		out.println(js);
		out.flush();
		out.close();
	}

	public static void turnLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		String message="无此权限";
		StringBuilder js = new StringBuilder("{\"result\":0,\"message\":\""+message+"\"}");
		PrintWriter out = response.getWriter();
		out.println(js.toString());
		out.flush();
		out.close();
	}
	
//	public static void turnLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		StringBuilder builder=new StringBuilder();
//		builder.append("<script type=\"text/javascript\">");
//		builder.append("alert('网页过期，请重新登录！');");
//		builder.append("window.top.location.href='");
//		builder.append("www.baidu.com");
//		builder.append("';");
//		builder.append("</script>");
//		out.println(builder);
//		out.flush();
//		out.close();
//	}

	public static void noPerRedirect(HttpServletRequest request, HttpServletResponse response, String permission)
			throws IOException {
		String rheader = request.getHeader("x-requested-with");
		if ((rheader != null) && (rheader.equalsIgnoreCase("XMLHttpRequest"))) {
			response.setHeader("sessionstatus", "timeout");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter wirter = response.getWriter();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("code", -1);
			result.put("msg", "无此权限!");
			wirter.write(result.toString());
			wirter.flush();
			wirter.close();
		} else {
			// 获取权限名称
			// WHYPrivileges privileges = null;
			// try {
			// privileges = WHYPrivileges.findFirst(WHYPrivileges.class,
			// "status=2 and code=? ", new Object[] { permission },
			// null);
			// } catch (ActiveRecordException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			StringBuilder js = new StringBuilder("<script>");
			// js.append("var win=window;");
			// js.append("while(win.parent!=window.top){win=win.parent;}");
			// js.append("win.parent.location.href='")
			// .append(request.getContextPath())
			// .append("/index/noPermisson?id="
			// + (privileges == null ? "-1" : privileges.getId()
			// .toString()) + "';");// 传入权限id

			js.append("</script>");

			PrintWriter out = response.getWriter();
			out.println(js);
			out.flush();
			out.close();
		}
	}

	public static boolean antiSqlInjectio(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Enumeration params = request.getParameterNames();
		String sql = "";
		while (params.hasMoreElements()) {
			// 得到参数名
			String name = params.nextElement().toString();
			// System.out.println("name===========================" + name +
			// "--");
			// 得到参数对应值
			String[] value = request.getParameterValues(name);
			for (int i = 0; i < value.length; i++) {
				sql = sql + value[i];
			}
		}
		boolean flag = sqlValidate(sql);
		return flag;
	}

	/**
	 * 
	 * @description TODO <br/>
	 * 
	 * @param sql攻击字符 @return boolean @throws
	 */
	protected static boolean sqlValidate(String str) {
		str = str.toLowerCase();// 统一转为小写
		String badStr = "'|exec|execute|insert|select|delete|update|count|drop|%|chr|mid|master|truncate|"
				+ "char|declare|sitename|net user|xp_cmdshell|+|" + "table|from|grant|use|group_concat|column_name|"
				+ "information_schema.columns|table_schema|union|where|order|by|" + "or|like|#";// 过滤掉的sql关键字，可以手动添加
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i]) >= 0) {
				System.out.println("攻击字符" + badStrs[i]);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @description 参数含有攻击字符跳转 <br/>
	 * 
	 * @param request @param response @throws IOException void @throws
	 */
	public static void paramError(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String rheader = request.getHeader("x-requested-with");
		if ((rheader != null) && (rheader.equalsIgnoreCase("XMLHttpRequest"))) {
			response.setHeader("sessionstatus", "timeout");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter wirter = response.getWriter();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("code", -1);
			result.put("msg", "无法查询，含有攻击字符!");
			wirter.write(result.toString());
			wirter.flush();
			wirter.close();
		} else {
			StringBuilder js = new StringBuilder("<script>");
			js.append("var win=window;");
			js.append("while(win.parent!=window.top){win=win.parent;}");
			js.append("win.parent.location.href='").append(request.getContextPath()).append("/index/error';");

			js.append("</script>");

			PrintWriter out = response.getWriter();
			out.println(js);
			out.flush();
			out.close();
		}
	}

	public static SessionUser getLoginedUser() {
		SessionUser sessionUser = null;
		try {
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser.isAuthenticated()) {
				sessionUser = (SessionUser) currentUser.getPrincipal();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionUser;
	}

}
