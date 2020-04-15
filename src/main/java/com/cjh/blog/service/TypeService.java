package com.cjh.blog.service;

import com.cjh.blog.entity.Type;

import java.util.List;

public interface TypeService {

    List<Type> selectTypes();

    int saveType(Type type);

    Type getType(Long id);

    int updateType(Type type);

    int deleteType(Long id);

    Type getTypeByName(String name);

    List<Type> selectIndexTypes();

    List<Type> selectIndexTypes2();
}
