package com.zp.springsecurity.mapper;

import com.zp.springsecurity.entity.Role;
import com.zp.springsecurity.entity.RoleExample;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    @Select("select * from role A inner join user_role_ref B on A.id = B.role_id where B.user_id = #{userId}")
    List<Role> selectUserRole(Integer userId);

    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}