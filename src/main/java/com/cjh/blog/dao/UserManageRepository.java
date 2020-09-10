package com.cjh.blog.dao;

import com.cjh.blog.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserManageRepository {

    List<User> selectUsers();

    int deleteUser(Long id);

    String selectUser(Long id);
}
