package com.crazy.crowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.crazy.crowdfunding.bean.Role;

public interface RoleDao {

	List<Role> queryLimit(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void insert(@Param("roleName")String roleName);

	@Select("select * from t_role where id = ${id}")
	Role queryById(@Param("id")Integer id);

	void update(@Param("id")Integer id, @Param("roleName")String roleName);

	@Delete("delete from t_role where id = #{id}")
	void deleteById(Integer id);

	@Select("select * from t_role")
	List<Role> queryAll();

	void insertRolePermission(Map<String, Object> paramMap);

	void deleteRolePermissions(Map<String, Object> paramMap);


}
