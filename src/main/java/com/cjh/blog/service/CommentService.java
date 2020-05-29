package com.cjh.blog.service;

import com.cjh.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> selectCommentsByBlogId(Long id);

    int saveComment(Comment comment);

    int deleteCommentByBlogId(Long blogId);

    int updateAdminComment(Comment comment);

    Long selectCommentCount(Long blogId);
}
