package com.crazy.crowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crazy.crowdfunding.bean.Role;
import com.crazy.crowdfunding.dao.RoleDao;
import com.crazy.crowdfunding.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;
	
	public List<Role> queryLimit(Map<String, Object> map) {
		return roleDao.queryLimit(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return roleDao.queryTotal(map);
	}

	public void insert(String roleName) {
		roleDao.insert(roleName);
	}

	public Role queryById(Integer id) {
		return roleDao.queryById(id);
	}

	public void update(Integer id, String roleName) {
		roleDao.update(id, roleName);
	}

	public void deleteById(Integer id) {
		roleDao.deleteById(id);
	}

	public List<Role> queryAll() {
		return roleDao.queryAll();
	}

	public void insertRolePermission(Map<String, Object> paramMap) {
		//更新之前先清空角色所有的许可
		roleDao.deleteRolePermissions(paramMap);
		roleDao.insertRolePermission(paramMap);
	}

}
