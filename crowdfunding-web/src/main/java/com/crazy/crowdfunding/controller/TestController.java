package com.crazy.crowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crazy.crowdfunding.bean.User;
import com.crazy.crowdfunding.service.UserService;


@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	@ResponseBody
	@RequestMapping("/queryAll")
	public Object queryAll(){
		List<User> users = userService.queryAll();
		return users;
	}
	
	@RequestMapping("/dologin")
	public String dologin(){
		return "main";
	}
	
}
