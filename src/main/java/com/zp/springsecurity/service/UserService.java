package com.zp.springsecurity.service;

import com.zp.springsecurity.entity.Role;
import com.zp.springsecurity.entity.User;
import com.zp.springsecurity.entity.UserExample;
import com.zp.springsecurity.mapper.RoleMapper;
import com.zp.springsecurity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    public User findUserByName(String username){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if(users == null || users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    public List<Role> getUserRoles(Integer userId){
        return roleMapper.selectUserRole(userId);
    }

    /**
     * 返回null时代表认证失败
     * @param username 用户在浏览器上输入的用户名
     * @return UserDetails spring security封闭的用户结构
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.findUserByName(username);

        if(user == null) {
            return null;
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        List<Role> userRoles = this.getUserRoles(user.getId());
        for (Role userRole : userRoles) {
            SimpleGrantedAuthority simpleGrantedAuthority =
                    new SimpleGrantedAuthority(userRole.getRoleName());
            authorities.add(simpleGrantedAuthority);
        }


        /**
         * UserDetails是个接口，User是其实现类，有三个参数
         * 第一个参数为数据库里的用户名
         * 第二个参数为数据库里的密码
         * 第三个参数为数据库的用户的角色
         */
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authorities);

        return userDetails;
    }

}
