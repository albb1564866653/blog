package com.cjh.blog.dao;

import com.cjh.blog.entity2.CommentManage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentManageRepository {

    List<CommentManage> selectComments();

    List<CommentManage> selectCommentObj(Long id);

    Long selectIdByNickName(String nickname);

    int deleteComment(Long id);

    int deleteCommentObj(Long id);

    int deleteCommentByNickName(String nickname);
}
