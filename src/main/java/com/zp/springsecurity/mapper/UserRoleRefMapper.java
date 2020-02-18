package com.zp.springsecurity.mapper;

import com.zp.springsecurity.entity.UserRoleRef;
import com.zp.springsecurity.entity.UserRoleRefExample;

import java.util.List;

public interface UserRoleRefMapper {
    long countByExample(UserRoleRefExample example);

    int deleteByExample(UserRoleRefExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleRef record);

    int insertSelective(UserRoleRef record);

    List<UserRoleRef> selectByExample(UserRoleRefExample example);

    UserRoleRef selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRoleRef record);

    int updateByPrimaryKey(UserRoleRef record);
}