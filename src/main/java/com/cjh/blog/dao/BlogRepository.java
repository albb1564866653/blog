package com.cjh.blog.dao;

import com.cjh.blog.entity.Blog;
import com.cjh.blog.entity2.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlogRepository {

    Blog selectBlogById(Long id);

    List<BlogQuery> selectBlogs();

    List<IndexBlog> selectIndexBlogs();

    List<BlogQuery> selectNewRecommendBlogs();

    List<BlogQuery> selectNewBlogs();

    List<IndexBlog> searchBlogs(String str);

    List<BlogQuery> searchBlog(SearchBlog searchBlog);

    List<IndexBlog> selectBlogsByTypeId(Long typeId);

    List<IndexBlog> selectBlogsByTagId(Long tagId);

    DetailedBlog selectDetailedBlog(Long id);

    int saveBlog(Blog blog);

    int saveBlogAndTag(BlogAndTag blogAndTag);

    int updateBlogAndTag(BlogAndTag blogAndTag);

    int updateBlogAndTag2(BlogAndTag blogAndTag);

    int deleteBlogAndTag(Long id);

    int deleteBlogAndTag2(Long id);

    int deleteBlog(Long id);

    int updateBlog(Blog blog);

    int changeViews(Long id);

    Long isExistTagId(String tagId);

    Long isExistTypeId(Long typeId);
}
