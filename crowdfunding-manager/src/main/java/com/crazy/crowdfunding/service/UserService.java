package com.crazy.crowdfunding.service;

import java.util.List;



import java.util.Map;

import com.crazy.crowdfunding.bean.User;

public interface UserService {

	List<User> queryAll();

	User queryByUser(User user);

	List<User> pageQuery(Map<String, Object> map);

	int pageQueryTotal(Map<String, Object> map);

	int insertUser(User user);

	User queryById(Integer id);

	void updateUser(User user);

	void deleteById(Integer id);

	void deleteBatch(Map<String, Object> map);

	void insertUserRoles(Map<String, Object> map);

	void deleteAssignroleids(Map<String, Object> map);

	List<Integer> queryRoleidsByUserid(Integer id);


}
