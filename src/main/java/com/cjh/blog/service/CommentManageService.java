package com.cjh.blog.service;

import com.cjh.blog.entity2.CommentManage;

import java.util.List;

public interface CommentManageService {

    List<CommentManage> selectComments();

    int deleteComment(Long id);


}
