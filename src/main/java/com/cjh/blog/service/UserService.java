package com.cjh.blog.service;

import com.cjh.blog.entity.User;

public interface UserService {
    User checkUser(String username, String password);

    User selectUser(String username);

    int updateUserInfo(User user);

    String getAvatar();

    int addUser(User user);
}
