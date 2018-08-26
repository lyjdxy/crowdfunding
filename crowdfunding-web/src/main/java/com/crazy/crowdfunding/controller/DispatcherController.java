package com.crazy.crowdfunding.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crazy.crowdfunding.bean.AjaxResult;
import com.crazy.crowdfunding.bean.Permission;
import com.crazy.crowdfunding.bean.User;
import com.crazy.crowdfunding.service.PermissionService;
import com.crazy.crowdfunding.service.UserService;

@Controller
public class DispatcherController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	@RequestMapping("/error")
	public String error(){
		return "error";
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session){
//		session.removeAttribute("name");
		session.invalidate();
		return "login";
	}
	
	@RequestMapping("/main")
	public String main(){
		return "main";
	}
	
	@ResponseBody
	@RequestMapping("/doAJAXLogin")
	public Object doAjaxLogin(User user,HttpSession session){
		//�����ݿ�У��
		User dbUser = userService.queryByUser(user);
		AjaxResult result = new AjaxResult();
		
		if(dbUser != null){
			//����session����
			session.setAttribute("user", dbUser);
			
			//��ȡ�û���Ӧ�������Ϣ
			List<Permission> ps = permissionService.queryPermissionsByUser(dbUser);
			
			//������ʾtree�����Map
			Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
			
			Permission root = null;
			
			//��ȡ��Ӧ�û������Ȩ�޵�uri��ַ���ϣ�����������
			Set<String> uriSet = new HashSet<String>();
			for(Permission p : ps){
				permissionMap.put(p.getId(), p);
				if(p.getUrl() != null || !"".equals(p.getUrl())){
					uriSet.add(session.getServletContext().getContextPath() + p.getUrl());
				}
			}
			session.setAttribute("authUriSet", uriSet);
			
			for(Permission p : ps){
				Permission child = p;
				if(child.getPid() == 0){
					//����Ǹ��ڵ�
					root = p;
				}else{
					//������Ǹ������Ȼ�ø��ڵ㣬����ƴ��
					Permission parent = permissionMap.get(child.getPid());
					parent.getChildren().add(child);
				}
			}
			
			session.setAttribute("rootPermission", root);
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return result;
	}
}
	