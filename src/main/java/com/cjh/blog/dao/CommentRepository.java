package com.cjh.blog.dao;

import com.cjh.blog.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository {
    List<Comment> selectCommentsByBlogId(Long id);

    int saveComment(Comment comment);

    int deleteCommentByBlogId(Long blogId);

    Comment selectCommentByParentCommentId(Long id);

    int updateAdminComment(Comment comment);
}
