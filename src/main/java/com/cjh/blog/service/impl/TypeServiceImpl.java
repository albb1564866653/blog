package com.cjh.blog.service.impl;

import com.cjh.blog.NotFoundException;
import com.cjh.blog.dao.TypeRepository;
import com.cjh.blog.entity.Type;
import com.cjh.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> selectTypes() {
        return typeRepository.selectTypes();
    }

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeRepository.saveType(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.getType(id);
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        Type t = typeRepository.getType(type.getId());
        if(t==null){
            throw new NotFoundException("不存在该分类！");
        }
        return typeRepository.updateType(type);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        return typeRepository.deleteType(id);
    }

    @Override
    public Type getTypeByName(String name) {

        return typeRepository.getTypeByName(name);
    }

    @Override
    public List<Type> selectIndexTypes() {
        return typeRepository.selectIndexTypes();
    }

    @Override
    public List<Type> selectIndexTypes2() {
        return typeRepository.selectIndexTypes2();
    }
}
