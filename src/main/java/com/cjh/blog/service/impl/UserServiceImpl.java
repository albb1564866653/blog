package com.cjh.blog.service.impl;

import com.cjh.blog.dao.UserRepository;
import com.cjh.blog.entity.User;
import com.cjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.checkUser(username, password);
        return user;
    }

    @Override
    public User selectUser(String username) {
        return userRepository.selectUser(username);
    }

    @Override
    public int updateUserInfo(User user) {
        return userRepository.updateUserInfo(user);
    }

    @Override
    public String getAvatar() {
        return userRepository.getAvatar();
    }
}
