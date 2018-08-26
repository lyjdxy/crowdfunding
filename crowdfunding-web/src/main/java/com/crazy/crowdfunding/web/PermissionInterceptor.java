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
 * �����Ȩ��������
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private PermissionService permissionService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getSession().getServletContext().getContextPath();
		
		//��ȡ�û��������ַ
		String uri = request.getRequestURI();
		
		
		//��ѯ������Ҫ��֤�ĵ�ַ�ļ���
		List<Permission> ps = permissionService.queryAll();
		Set<String> uriSet = new HashSet<String>();
		for(Permission p : ps){
			if(p.getUrl() != null || !"".equals(p.getUrl())){
				uriSet.add(path + p.getUrl());
			}
		}
		
		//�ж������ַ�Ƿ���Ҫ����Ȩ����֤
		if(uriSet.contains(uri)){
			//Ȩ����֤
			//�жϵ�ǰ�û��Ƿ�ӵ�ж�Ӧ��Ȩ�ޣ��ȴ�session�л�ȡ�û�ӵ�е���ɵ�ַ����
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
