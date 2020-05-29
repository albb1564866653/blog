package com.cjh.blog.controller;

import com.cjh.blog.entity.Tag;
import com.cjh.blog.entity.Type;
import com.cjh.blog.entity2.BlogQuery;
import com.cjh.blog.entity2.DetailedBlog;
import com.cjh.blog.entity2.IndexBlog;
import com.cjh.blog.service.*;
import com.cjh.blog.util.GetOtherData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/")
    public String index(Model model, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum, 8);
        List<IndexBlog> indexBlogs = blogService.selectIndexBlogs();
        //博客信息
        PageInfo<IndexBlog> blogPage = new PageInfo<>(indexBlogs);

        //右侧分类信息-----------------------------------------------
        List<Type> result1 = typeService.selectIndexTypes();
        List<Type> types =new ArrayList<>();

        if(result1.size()==0){//没有博客以及分类
            types=null;
        }else{

            if(result1.size()>=4){
//当result1>=要for遍历的次数的时候才进行for循环，因为当result1的size值小于要for遍历的次数时，会出现数组越界
                for(int i=0;i<=3;i++){//只装了4条数据进types=>首页只显示4条分类
                    types.add(result1.get(i));
                }

            }else if(result1.size()<4){
                types=result1;
            }

        }
        //-----------------------------------------------

        //右侧标签信息-----------------------------------------------
        List<Tag> result2 = tagService.selectIndexTags();
        List<Tag> tags=new ArrayList<>();
        if(result2.size()==0){//没有博客以及标签
            tags=null;
        }else{

            if(result2.size()>=20){
//当result2>=for遍历的次数的时候才进行for循环，因为当result2的size值小于要for遍历的次数时，会出现数组越界
                for(int i=0;i<=19;i++){//只装了20条数据进tags=>首页只显示20个标签

                    tags.add(result2.get(i));

                }

            }else if(result2.size()<20){
                tags=result2;
            }

        }
        //-----------------------------------------------

        //最新推荐博客信息
        List<BlogQuery> newRecommendBlogs = blogService.selectNewRecommendBlogs();
        model.addAttribute("blogPage", blogPage);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        model.addAttribute("newRecommendBlogs", newRecommendBlogs);

        GetOtherData.getNewBlogEstAndAvatar(blogService,userService,model);
        return "index";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(value = "query") String query,Model model, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        System.out.println("query的值："+query);
        PageHelper.startPage(pageNum, 5);
        List<IndexBlog> searchBlogs = blogService.searchBlogs("%" + query + "%");
        PageInfo<IndexBlog> blogPage = new PageInfo<>(searchBlogs);
        model.addAttribute("blogPage", blogPage);
        model.addAttribute("query", query);

        GetOtherData.getNewBlogEstAndAvatar(blogService,userService,model);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        DetailedBlog detailedBlogs = blogService.getAndConvert(id);
        System.out.println("结果："+detailedBlogs);

        model.addAttribute("blog", detailedBlogs);

        Long commentCount=commentService.selectCommentCount(id);
        model.addAttribute("commentCount", commentCount);

        GetOtherData.getNewBlogEstAndAvatar(blogService,userService,model);
        return "blog";
    }

//    @GetMapping("/footer/newBlog")
//    public String newBlogs(Model model){
//        System.out.println("/footer/newBlog...............");
//        //最新博客信息
//        List<BlogQuery> newBlogs = blogService.selectNewBlogs();
//        model.addAttribute("newBlogs", newBlogs);
//        return "_fragments :: newBlogList";
//    }

}
