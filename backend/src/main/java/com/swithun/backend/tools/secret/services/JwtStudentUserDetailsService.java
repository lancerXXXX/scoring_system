/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-07 16:26:44
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-06 19:52:32
 */
package com.swithun.backend.tools.secret.services;

import com.swithun.backend.dao.StudentRepository;
import com.swithun.backend.entity.StudentEntity;
import com.swithun.backend.tools.secret.model.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("JwtStudentUserDetailsService")
public class JwtStudentUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentR;

    // @Autowired
    // private PasswordEncoder bcryptEncoder;

    /**
     * @description: 通过用户名 在数据库查询是否有该用户
     * @param {*}
     * @return {*}
     * @Autowired
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("StudentUserDetailsService根据username查询数据库");

        // 1. 根据username查询数据库
        StudentEntity user = studentR.findByName(username);
        // 2.1 如果没有查询到，抛出异常
        if (user == null) {
            throw new UsernameNotFoundException("Student not found with username: " + username);
        }
        // 2.2 如果查询到，返回User(userdetials的实现类) (姓名，加密后的密码，权限&角色)
        return new User(user.getName(), user.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("student,ROLE_student"));

    }

    /**
     * inserting user details
     * 
     * @param user
     * @return
     */
    public StudentEntity save(UserDTO user) {
        StudentEntity newUser = new StudentEntity();
        newUser.setName(user.getUsername());
        PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return studentR.save(newUser);
    }
}