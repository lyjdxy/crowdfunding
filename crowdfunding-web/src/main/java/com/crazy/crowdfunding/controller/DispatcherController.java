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
		//从数据库校验
		User dbUser = userService.queryByUser(user);
		AjaxResult result = new AjaxResult();
		
		if(dbUser != null){
			//放入session域中
			session.setAttribute("user", dbUser);
			
			//获取用户对应的许可信息
			List<Permission> ps = permissionService.queryPermissionsByUser(dbUser);
			
			//用于显示tree结果的Map
			Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
			
			Permission root = null;
			
			//获取对应用户的许可权限的uri地址集合，用于拦截器
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
					//如果是根节点
					root = p;
				}else{
					//如果不是根，则先获得父节点，在来拼接
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
	