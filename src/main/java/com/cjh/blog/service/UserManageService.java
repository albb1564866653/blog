package com.cjh.blog.service;

import com.cjh.blog.entity.User;

import java.util.List;

public interface UserManageService {

    List<User> selectUsers();

    int deleteUser(Long id);
}
