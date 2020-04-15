package com.cjh.blog.dao;

import com.cjh.blog.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User checkUser(String username, String password);

    User selectUser(String username);

    int updateUserInfo(User user);

    String getAvatar();
}
