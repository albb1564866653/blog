package com.cjh.blog.service.impl;

import com.cjh.blog.dao.CommentManageRepository;
import com.cjh.blog.entity2.CommentManage;
import com.cjh.blog.service.CommentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentManageServiceImpl implements CommentManageService {

    @Autowired
    private CommentManageRepository commentManageRepository;

    @Override
    public List<CommentManage> selectComments() {
        return commentManageRepository.selectComments();
    }

    @Override
    public int deleteComment(Long id) {
        int result = commentManageRepository.deleteComment(id);
        //查找传入的id的用户评论是否被人回复
        List<CommentManage> commentManages = commentManageRepository.selectCommentObj(id);
        if(commentManages.size()>0){
            //将回复他的评论也删除
            commentManageRepository.deleteCommentObj(id);
        }

        return result;
    }

}
