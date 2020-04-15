package com.cjh.blog.entity;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class Tag {

    private Long id;
    @NotBlank(message = "标签名称不能为空！")
    private String name;
    private List<Blog> blogs=new ArrayList<>();;//多对多关系

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
