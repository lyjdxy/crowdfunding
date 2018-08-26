package com.crazy.crowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crazy.crowdfunding.bean.AjaxResult;
import com.crazy.crowdfunding.bean.Page;
import com.crazy.crowdfunding.bean.Role;
import com.crazy.crowdfunding.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@RequestMapping("/index")
	public String index(){
		return "role/index";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Object list(Integer pageNo, Integer size,@RequestParam(required=false) String likeSel){
		
		AjaxResult result = new AjaxResult();
		try{
			int start = (pageNo - 1) * size;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("size", size);
			map.put("likeSel", likeSel);
			List<Role> roles = roleService.queryLimit(map);
			
			//获取总记录数，用于创建页码导航条
			int totalSize = roleService.queryTotal(map);
			//存入总页数
			int totalNo = 0;
			if(totalSize % size == 0){
				//能整除
				totalNo = totalSize / size;
			}else{
				//不能整除，向上取整（因为这是int型数据，使用Math.ceil也没用）
				totalNo = totalSize / size + 1;
			}
			
			Page<Role> rolePage = new Page<Role>();
			rolePage.setDatas(roles);
			rolePage.setPageNo(pageNo);
			rolePage.setTotalNo(totalNo);
			rolePage.setTotalSize(totalSize);
			
			result.setData(rolePage);
			result.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/add")
	public String add(){
		return "role/add";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(String roleName){
		AjaxResult result = new AjaxResult();
		try{
			roleService.insert(roleName);
			result.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id, Model model){
		Role role = roleService.queryById(id);
		model.addAttribute("role", role);
		return "role/edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Integer id, String roleName){
		AjaxResult result = new AjaxResult();
		try{
			roleService.update(id,roleName);
			result.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id){
		System.out.println(id);
		AjaxResult result = new AjaxResult();
		try{
			roleService.deleteById(id);
			result.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/assign")
	public String assign() {
		return "role/assign";
	}
	
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign( Integer roleid, Integer[] permissionids ) {
		AjaxResult result = new AjaxResult();
		
		try {
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("roleid", roleid);
			paramMap.put("permissionids", permissionids);
			roleService.insertRolePermission(paramMap);
			
			result.setSuccess(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
}
