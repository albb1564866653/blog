package com.cjh.blog.entity2;

/**
 * 搜索博客的查询条件类
 */
public class SearchBlog {

    private String title;
    private Long typeId;
    private String recommend;

    public SearchBlog() {
    }

    public SearchBlog(String title, Long typeId, String recommend) {
        this.title = title;
        this.typeId = typeId;
        this.recommend = recommend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "SearchBlog{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommend='" + recommend + '\'' +
                '}';
    }
}
