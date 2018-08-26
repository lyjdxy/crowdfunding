package com.crazy.crowdfunding.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.crazy.crowdfunding.bean.Role;

@Service
public interface RoleService {

	List<Role> queryLimit(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void insert(String roleName);

	Role queryById(Integer id);

	void update(Integer id, String roleName);

	void deleteById(Integer id);

	List<Role> queryAll();

	void insertRolePermission(Map<String, Object> paramMap);

}
