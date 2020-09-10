package com.cjh.blog.service.impl;

import com.cjh.blog.dao.CommentManageRepository;
import com.cjh.blog.dao.UserManageRepository;
import com.cjh.blog.dao.UserRepository;
import com.cjh.blog.entity.User;
import com.cjh.blog.entity2.CommentManage;
import com.cjh.blog.service.UserManageService;
import com.cjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    private UserManageRepository userManageRepository;
    @Autowired
    private CommentManageRepository commentManageRepository;


    @Override
    public List<User> selectUsers() {
        return userManageRepository.selectUsers();
    }

    @Override
    public int deleteUser(Long id) {
        //删除之前先查出该用户的昵称
        String nickname = userManageRepository.selectUser(id);

        //删除用户
        int result = userManageRepository.deleteUser(id);
        if(result>0){
           //获取该用户的id
            Long objId = commentManageRepository.selectIdByNickName(nickname);
            //用该用户的昵称删除他的评论
            commentManageRepository.deleteCommentByNickName(nickname);

            //查找传入的id的用户评论是否被人回复
            List<CommentManage> commentManages = commentManageRepository.selectCommentObj(objId);
            if(commentManages.size()>0){
                //将回复他的评论也删除
                commentManageRepository.deleteCommentObj(objId);
            }
        }
        return result;
    }
}
