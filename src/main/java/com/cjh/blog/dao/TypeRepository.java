package com.cjh.blog.dao;

import com.cjh.blog.entity.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository {

    List<Type> selectTypes();

    int saveType(Type type);

    Type getType(Long id);

    int updateType(Type type);

    int deleteType(Long id);

    List<Type> getAdminType();

    Type getTypeByName(String name);

    List<Type> selectIndexTypes();

    List<Type> selectIndexTypes2();

}
