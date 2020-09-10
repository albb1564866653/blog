package com.cjh.blog.service.impl;

import com.cjh.blog.NotFoundException;
import com.cjh.blog.dao.BlogRepository;
import com.cjh.blog.dao.CommentRepository;
import com.cjh.blog.dao.TypeRepository;
import com.cjh.blog.entity.Blog;
import com.cjh.blog.entity.Tag;
import com.cjh.blog.entity2.*;
import com.cjh.blog.service.BlogService;
import com.cjh.blog.service.TagService;
import com.cjh.blog.util.MarkdownUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {
    private int i = -1;

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Blog selectBlogById(Long id) {
        return blogRepository.selectBlogById(id);
    }

    @Override
    public List<BlogQuery> selectBlogs() {
        return blogRepository.selectBlogs();
    }

    @Override
    public List<IndexBlog> selectIndexBlogs() {
        return blogRepository.selectIndexBlogs();
    }

    @Override
    public List<BlogQuery> selectNewRecommendBlogs() {
        return blogRepository.selectNewRecommendBlogs();
    }

    @Override
    public List<BlogQuery> selectNewBlogs() {
        return blogRepository.selectNewBlogs();
    }

    @Override
    public List<IndexBlog> searchBlogs(String str) {
        return blogRepository.searchBlogs(str);
    }

    @Override
    public List<BlogQuery> searchBlog(SearchBlog searchBlog) {
        return blogRepository.searchBlog(searchBlog);
    }

    @Override
    public DetailedBlog selectDetailedBlog(Long id) {
        return blogRepository.selectDetailedBlog(id);
    }

    @Override
    public List<IndexBlog> selectBlogsByTypeId(Long typeId) {
        return blogRepository.selectBlogsByTypeId(typeId);
    }

    @Override
    public List<IndexBlog> selectBlogsByTagId(Long tagId) {
        return blogRepository.selectBlogsByTagId(tagId);
    }

    @Override
    public DetailedBlog getAndConvert(Long id) {
        //浏览次数+1
        blogRepository.changeViews(id);

        DetailedBlog detailedBlog = blogRepository.selectDetailedBlog(id);
        if (detailedBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        DetailedBlog db = new DetailedBlog();
        BeanUtils.copyProperties(detailedBlog, db);
        String content = db.getContent();
        db.setContent(MarkdownUtil.markdownToHtmlExtensions(content));
        return db;
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        if (blog.getId() == null) {//新建
            //给blog初始化属性
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
            //将标签的数据存到t_blogs_tag表中
            List<Tag> tags = blog.getTags();
            //随机数作为blog的id值
            i = (int) (Math.random() * 1000) + 1;

            Blog isExit = null;
            while (true){//循环判断随机生成的id 是否已存在
                isExit=blogRepository.selectBlogById((long) i);
                if (isExit != null) {//这个随机数存在于数据库，不能使用
                i = (int) (Math.random() * 1000) + 1;//重新生成随机数
                }else{
                    break;
                }
            }

            blog.setId((long) i);

            BlogAndTag blogAndTag = null;
            for (Tag tag : tags) {
                blogAndTag = new BlogAndTag(tag.getId(), blog.getId());
                blogRepository.saveBlogAndTag(blogAndTag);
            }

        }
        return blogRepository.saveBlog(blog);
    }

    @Transactional
    @Override
    public int deleteBlog(Long id) {
        blogRepository.deleteBlogAndTag(id);
        commentRepository.deleteCommentByBlogId(id);

        return blogRepository.deleteBlog(id);
    }

    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        System.out.println("genxin......." + blog);
        blogRepository.deleteBlogAndTag(blog.getId());
        //将标签的数据存到t_blogs_tag表中
        String tag_ids = blog.getTagIds();//1,2,3
        List<Long> tagIds = TagServiceImpl.convertToList(tag_ids);//[1,2,3]
        BlogAndTag blogAndTag = null;
        for (Long tagId : tagIds) {
            blogAndTag = new BlogAndTag(tagId, blog.getId());
            blogRepository.saveBlogAndTag(blogAndTag);
        }

        return blogRepository.updateBlog(blog);
    }

    @Override
    public Long isExistTagId(String tagId) {
        return blogRepository.isExistTagId(tagId);
    }

    @Override
    public Long isExistTypeId(Long typeId) {
        return blogRepository.isExistTypeId(typeId);
    }
}
