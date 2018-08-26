package com.crazy.crowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.crazy.crowdfunding.bean.User;

public interface UserDao {

	@Select("select * from t_user")
	List<User> queryAll();

	@Select("select * from t_user where account = #{Account} and password = #{password}")
	User queryByUser(User user);

	List<User> pageQuery(Map<String, Object> map);

	int pageQueryTotal(Map<String, Object> map);

	int insertUser(User user);

	@Select("select * from t_user where id = #{id}")
	User queryById(Integer id);

	void updateUser(User user);

	void deleteById(Integer id);

	void deleteBatch(Map<String, Object> map);

	void insertUserRoles(Map<String, Object> map);

	void deleteAssignroleids(Map<String, Object> map);

	@Select("select r_id from t_UR where u_id = #{id}")
	List<Integer> queryRoleidsByUserid(Integer id);

}
