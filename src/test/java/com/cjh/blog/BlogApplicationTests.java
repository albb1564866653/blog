package com.cjh.blog;

import com.cjh.blog.dao.*;
import com.cjh.blog.entity.Blog;
import com.cjh.blog.entity.Comment;
import com.cjh.blog.entity.Tag;
import com.cjh.blog.entity.Type;
import com.cjh.blog.entity2.BlogQuery;
import com.cjh.blog.entity2.DetailedBlog;
import com.cjh.blog.entity2.IndexBlog;
import com.cjh.blog.entity2.SearchBlog;
import com.cjh.blog.service.BlogService;
import com.cjh.blog.service.TagService;
import com.cjh.blog.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        //测试
    }

}
