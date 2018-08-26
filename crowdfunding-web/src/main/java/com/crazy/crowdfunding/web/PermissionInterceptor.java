package com.crazy.crowdfunding.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.crazy.crowdfunding.bean.Permission;
import com.crazy.crowdfunding.service.PermissionService;

/**
 * 请求的权限拦截器
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private PermissionService permissionService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getSession().getServletContext().getContextPath();
		
		//获取用户的请求地址
		String uri = request.getRequestURI();
		
		
		//查询所有需要验证的地址的集合
		List<Permission> ps = permissionService.queryAll();
		Set<String> uriSet = new HashSet<String>();
		for(Permission p : ps){
			if(p.getUrl() != null || !"".equals(p.getUrl())){
				uriSet.add(path + p.getUrl());
			}
		}
		
		//判断这个地址是否需要进行权限验证
		if(uriSet.contains(uri)){
			//权限验证
			//判断当前用户是否拥有对应的权限，先从session中获取用户拥有的许可地址集合
			Set<String> authUriSet = (Set<String>) request.getSession().getAttribute("authUriSet");
			if(authUriSet.contains(uri)){
				return true;
			}else{
				response.sendRedirect(path + "/error");
				return false;
			}
		}
		
		return true;
	}
	

}
