package com.xinding.travel.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xinding.travel.security.SecurityUtil;

public class SessionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String[] filter = new String[] { "add", "delete", "update" };
		String uri = request.getRequestURI();
		String req = uri.substring(uri.lastIndexOf("/") + 1);
		boolean doFilter = false;
		if (uri.contains("admin")) {
			for (String s : filter) {
				if (s.equals(req)) {
					doFilter = true;
					break;
				}
			}
		}
		if (doFilter) {
			String permission = uri.substring(uri.indexOf("admin")+"admin".length()+1);
			Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.isPermitted(permission)) {
				SecurityUtil.turnLogin(request, response);
				return;
			}
			filterChain.doFilter(request, response);
		} else {
			filterChain.doFilter(request, response);
		}

	}

//	public static void main(String[] args) {
//		String[] Filter = new String[] { "add", "delete", "update" };
//		String s = "/travel-web/admin/scenic-project/delete";
//		String sb = "aaaaaaaaaaadminnnnnnnnnnnnnnnnnnnn";
//		System.out.println(s.substring(s.indexOf("admin")+"admin".length()+1));
//		System.out.println(s.substring(s.lastIndexOf("/")));
//		System.out.println(sb.contains("admin"));
//	}

}