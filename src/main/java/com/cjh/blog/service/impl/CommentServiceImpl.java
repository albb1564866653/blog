package com.cjh.blog.service.impl;

import com.cjh.blog.dao.CommentRepository;
import com.cjh.blog.entity.Comment;
import com.cjh.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

//    @Override
//    public List<Comment> selectCommentsByBlogId(Long id) {
//        List<Comment> comments=commentRepository.selectCommentsByBlogId(id);
//        System.out.println("selectCommentsByBlogId~~~~~~~~~~~~~"+comments);
//        return eachComment(comments);
//    }

    @Override
    public List<Comment> selectCommentsByBlogId(Long id) {

        return commentRepository.selectCommentsByBlogId(id);
    }

    @Transactional
    @Override
    public int saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        System.out.println("parentCommentId-----:" + parentCommentId);
        if (parentCommentId != -1) {
            System.out.println("父级评论：" + commentRepository.selectCommentByParentCommentId(parentCommentId));
            comment.setParentComment(commentRepository.selectCommentByParentCommentId(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        System.out.println("处理完毕的评论类：" + comment);
        return commentRepository.saveComment(comment);
    }

    @Transactional
    @Override
    public int deleteCommentByBlogId(Long blogId) {
        return commentRepository.deleteCommentByBlogId(blogId);
    }

    @Transactional
    @Override
    public int updateAdminComment(Comment comment) {
        return commentRepository.updateAdminComment(comment);
    }

    @Override
    public Long selectCommentCount(Long blogId) {
        return commentRepository.selectCommentCount(blogId);
    }

//    /**
//     * 循环每个顶级的评论节点
//     * @param comments
//     * @return
//     */
//    private List<Comment> eachComment(List<Comment> comments) {
//        List<Comment> commentsView = new ArrayList<>();
//        for (Comment comment : comments) {
//            Comment c = new Comment();
//            BeanUtils.copyProperties(comment,c);
//            commentsView.add(c);
//        }
//        //合并评论的各层子代到第一级子代集合中
//        combineChildren(commentsView);
//        return commentsView;
//    }
//
//    /**
//     *
//     * @param comments root根节点，blog不为空的对象集合
//     * @return
//     */
//    private void combineChildren(List<Comment> comments) {
//
//        for (Comment comment : comments) {
//            List<Comment> replys1 = comment.getReplyComments();
//            for(Comment reply1 : replys1) {
//                //循环迭代，找出子代，存放在tempReplys中
//                recursively(reply1);
//            }
//            //修改顶级节点的reply集合为迭代处理后的集合
//            comment.setReplyComments(tempReplys);
//            //清除临时存放区
//            tempReplys = new ArrayList<>();
//        }
//    }
//
//    //存放迭代找出的所有子代的集合
//    private List<Comment> tempReplys = new ArrayList<>();
//    /**
//     * 递归迭代，剥洋葱
//     * @param comment 被迭代的对象
//     * @return
//     */
//    private void recursively(Comment comment) {
//        tempReplys.add(comment);//顶节点添加到临时存放集合
//        if (comment.getReplyComments().size()>0) {
//            List<Comment> replys = comment.getReplyComments();
//            for (Comment reply : replys) {
//                tempReplys.add(reply);
//                if (reply.getReplyComments().size()>0) {
//                    recursively(reply);
//                }
//            }
//        }
//    }
}
