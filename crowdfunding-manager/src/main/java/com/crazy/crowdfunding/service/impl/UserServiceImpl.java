package com.crazy.crowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crazy.crowdfunding.bean.User;
import com.crazy.crowdfunding.dao.UserDao;
import com.crazy.crowdfunding.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	public List<User> queryAll() {
		return userDao.queryAll();
	}

	public User queryByUser(User user) {
		return userDao.queryByUser(user);
	}

	public List<User> pageQuery(Map<String, Object> map) {
		return userDao.pageQuery(map);
	}

	public int pageQueryTotal(Map<String, Object> map) {
		return userDao.pageQueryTotal(map);
	}

	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

	public User queryById(Integer id) {
		return userDao.queryById(id);
	}

	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	public void deleteById(Integer id) {
		userDao.deleteById(id);	
	}

	public void deleteBatch(Map<String, Object> map) {
		userDao.deleteBatch(map);
	}

	public void insertUserRoles(Map<String, Object> map) {
		userDao.insertUserRoles(map);
	}

	public void deleteAssignroleids(Map<String, Object> map) {
		userDao.deleteAssignroleids(map);
	}

	public List<Integer> queryRoleidsByUserid(Integer id) {
		return userDao.queryRoleidsByUserid(id);
	}

}
