package com.cjh.blog.service;

import com.cjh.blog.entity.Blog;
import com.cjh.blog.entity.Tag;
import com.cjh.blog.entity2.BlogQuery;
import com.cjh.blog.entity2.DetailedBlog;
import com.cjh.blog.entity2.IndexBlog;
import com.cjh.blog.entity2.SearchBlog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog selectBlogById(Long id);

    List<BlogQuery> selectBlogs();

    List<IndexBlog> selectIndexBlogs();

    List<BlogQuery> selectNewRecommendBlogs();

    List<BlogQuery> selectNewBlogs();

    List<IndexBlog> searchBlogs(String str);

    List<BlogQuery> searchBlog(SearchBlog searchBlog);

    DetailedBlog selectDetailedBlog(Long id);

    List<IndexBlog> selectBlogsByTypeId(Long typeId);

    List<IndexBlog> selectBlogsByTagId(Long tagId);

    DetailedBlog getAndConvert(Long id);

    int saveBlog(Blog blog);

    int deleteBlog(Long id);

    int updateBlog(Blog blog);

    Long isExistTagId(String tagId);

    Long isExistTypeId(Long typeId);
    
}
