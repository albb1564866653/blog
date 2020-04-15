package com.cjh.blog.entity2;

/**
 * 把博客和标签关系存到数据库中使用的类
 */
public class BlogAndTag2 {
    private String tagIds;

    private Long blogId;

    public BlogAndTag2() {
    }

    public BlogAndTag2(String tagIds, Long blogId) {
        this.tagIds = tagIds;
        this.blogId = blogId;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "BlogAndTag2{" +
                "tagIds='" + tagIds + '\'' +
                ", blogId=" + blogId +
                '}';
    }
}

