package com.crazy.crowdfunding.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.crazy.crowdfunding.bean.User;
import com.crazy.crowdfunding.service.RoleService;
import com.crazy.crowdfunding.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * �û���ҳ
	 * �������⣬��ҳ�������������������ʱ�����԰Ѷ�̬����Ϣ���첽ajax��ʾ�����и��õ��û�����
	 */
	/*
	@RequestMapping("/index")
	public String index(@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			@RequestParam(value="pageSize",required=false,defaultValue="2")Integer pageSize,Model model){
		
		//��ҳ��ѯ�����뿪ʼλ��start�ʹ�Сsize
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageNo - 1) * pageSize);
		map.put("size", pageSize);
		List<User> users = userService.pageQuery(map);
		
		model.addAttribute("users", users);
		//���뵱ǰҳ��
		model.addAttribute("pageNo",pageNo);
		//��ȡ�ܼ�¼�������ڴ���ҳ�뵼����
		int totalSize = userService.pageQueryTotal(map);
		//������ҳ��
		int totalNo = 0;
		if(totalSize % pageSize == 0){
			//������
			totalNo = totalSize / pageSize;
		}else{
			//��������������ȡ������Ϊ����int�����ݣ�ʹ��Math.ceilҲû�ã�
			totalNo = totalSize / pageSize + 1;
		}
		model.addAttribute("totalNo",totalNo);
		return "user/index1";
	}*/
	@RequestMapping("/index")
	public String index(){
		return "user/index";
	}
	//�첽�û���ҳ��ѯ
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText, Integer pageNo,Integer pageSize){
		
		AjaxResult result = new AjaxResult();
		
		try{
			//��ҳ��ѯ�����뿪ʼλ��start�ʹ�Сsize
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", (pageNo - 1) * pageSize);
			map.put("size", pageSize);
			map.put("queryText", queryText);
			List<User> users = userService.pageQuery(map);
			
			//��ȡ�ܼ�¼�������ڴ���ҳ�뵼����
			int totalSize = userService.pageQueryTotal(map);
			
			//������ҳ��
			int totalNo = 0;
			if(totalSize % pageSize == 0){
				//������
				totalNo = totalSize / pageSize;
			}else{
				//��������������ȡ������Ϊ����int�����ݣ�ʹ��Math.ceilҲû�ã�
				totalNo = totalSize / pageSize + 1;
			}
			
			//��װ��ҳ����Page
			Page<User> userPage = new Page<User>();
			userPage.setDatas(users);
			userPage.setPageNo(pageNo);
			userPage.setTotalNo(totalNo);
			userPage.setTotalSize(totalSize);
			
			//��װajax���ؽ������
			result.setData(userPage);
			result.setSuccess(true);
			
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@RequestMapping("/add")
	public String add(){
		return "user/add";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(User user){
		AjaxResult result = new AjaxResult();
		try{
			user.setPassword("123");
			user.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			userService.insertUser(user);
			result.setSuccess(true);
		}catch(Exception e ){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id, Model model){
		
		User user = userService.queryById(id);
		model.addAttribute("user",user);
		
		return "user/edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update(User user){
		AjaxResult result = new AjaxResult();
		try{
			userService.updateUser(user);
			result.setSuccess(true);
		}catch(Exception e ){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id){
		AjaxResult result = new AjaxResult();
		try{
			userService.deleteById(id);
			result.setSuccess(true);
		}catch(Exception e ){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/deleteBatch")
	public Object deleteBatch(Integer[] userid){
		System.out.println(userid.length);
		AjaxResult result = new AjaxResult();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userids", userid);
			userService.deleteBatch(map);
			result.setSuccess(true);
		}catch(Exception e ){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/toAssign")
	public String toAssign(Integer id, Model model){
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		
		//������еĽ�ɫ
		List<Role> roles = roleService.queryAll();
		
		//���ڴ���ѷ����δ�����ɫ
		List<Role> assignedRoles = new ArrayList<Role>();
		List<Role> unassignedRoles = new ArrayList<Role>();
		
		//����û��ѷ���Ľ�ɫ
		List<Integer> roleids = userService.queryRoleidsByUserid(id);
		
		for(Role role : roles){
			if(roleids.contains(role.getId())){
				assignedRoles.add(role);
			}else{
				unassignedRoles.add(role);
			}
		}
		model.addAttribute("assignedRoles",assignedRoles);
		model.addAttribute("unassignedRoles",unassignedRoles);
		return "assign";
	}
	
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(Integer userid, Integer[] unassignroleids){
		AjaxResult result = new AjaxResult();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userid);
			map.put("roleids", unassignroleids);
			userService.insertUserRoles(map);
			result.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/dounAssign")
	public Object dounAssign(Integer userid, Integer[] assignroleids){
		AjaxResult result = new AjaxResult();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userid);
			map.put("roleids", assignroleids);
			userService.deleteAssignroleids(map);
			result.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
}
