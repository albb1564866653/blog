package com.cjh.blog.entity2;

import java.util.Date;

public class CommentManage {
    private Long id;
    private String nickname;
    private String content;
    private Date createTime;
    private String title;
    private String commentObject;

    public CommentManage() {
    }

    public CommentManage(Long id, String nickname, String content, Date createTime, String title, String commentObject) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.createTime = createTime;
        this.title = title;
        this.commentObject = commentObject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommentObject() {
        return commentObject;
    }

    public void setCommentObject(String commentObject) {
        this.commentObject = commentObject;
    }

    @Override
    public String toString() {
        return "CommentManage{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                ", commentObject='" + commentObject + '\'' +
                '}';
    }
}
