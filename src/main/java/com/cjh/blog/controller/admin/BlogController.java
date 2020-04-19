package com.cjh.blog.controller.admin;

import com.cjh.blog.entity.Blog;
import com.cjh.blog.entity.Tag;
import com.cjh.blog.entity.Type;
import com.cjh.blog.entity.User;
import com.cjh.blog.entity2.BlogQuery;
import com.cjh.blog.entity2.SearchBlog;
import com.cjh.blog.service.BlogService;
import com.cjh.blog.service.TagService;
import com.cjh.blog.service.TypeService;
import com.cjh.blog.service.UserService;
import com.cjh.blog.util.GetOtherData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    //新增博客页面
    @GetMapping("/blogs/input")
    public String input(Model model){

        setTagAndType(model);
        model.addAttribute("blog",new Blog());

        GetOtherData.getNewBlogEstAndAvatar(blogService,userService,model);
        return "admin/blogs-input";
    }

    private void setTagAndType(Model model){
        model.addAttribute("tag",new Tag());
        model.addAttribute("types", typeService.selectTypes());
        model.addAttribute("tags", tagService.selectTags());
    }

    //修改博客页面
    @GetMapping("/blogs/update/{id}")
    public String update(@PathVariable Long id,Model model){
        Blog blog = blogService.selectBlogById(id);
        System.out.println("前端的信息："+blog);
        setTagAndType(model);
        model.addAttribute("blog",blogService.selectBlogById(id));
        model.addAttribute("updatePage","修改页面");

        GetOtherData.getNewBlogEstAndAvatar(blogService,userService,model);
        return "admin/blogs-input";
    }

    //显示
    @GetMapping("/blogs")
    public String blogs(Model model, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<BlogQuery> blogs = blogService.selectBlogs();
        PageInfo<BlogQuery> page = new PageInfo<>(blogs);
        model.addAttribute("page", page);

        //为了显示下拉框的所有分类信息
        List<Type> types = typeService.selectTypes();
        model.addAttribute("types", types);

        GetOtherData.getNewBlogEstAndAvatar(blogService,userService,model);
        return "admin/blogs";
    }

    //搜索
    @GetMapping("/blogs/search")
//    public String search(Model model,SearchBlog searchBlog
    public String search(Model model,String title,Long typeId,String recommend, @RequestParam(value = "pageNum",defaultValue = "1")
            Integer pageNum, RedirectAttributes redirectAttributes){
        SearchBlog searchBlog=new SearchBlog(title,typeId,recommend);
        System.out.println("前端的数据："+searchBlog);
        PageHelper.startPage(pageNum, 5);
        //=on，=1 才赋值1          =null，=0 赋值0
        if(searchBlog.getRecommend()!=null){
            if(searchBlog.getRecommend().equals("on")||searchBlog.getRecommend().equals("1")){//第一次点搜索传进来的||点分页传进来的
                searchBlog.setRecommend("1");
            } else if (searchBlog.getRecommend().equals("0")){//点分页传进来的
                searchBlog.setRecommend("0");
            }
        }else{//第一次点搜索传进来的
            searchBlog.setRecommend("0");
        }

        System.out.println("处理完的searchBlog----------------------"+searchBlog);
        List<BlogQuery> blogs = blogService.searchBlog(searchBlog);
        System.out.println("==================="+blogs);

        PageInfo<BlogQuery> page = new PageInfo<>(blogs);
        model.addAttribute("page", page);

        System.out.println("分页的数据----------------------"+page.getList());

        //为了显示下拉框的所有分类信息
        List<Type> types = typeService.selectTypes();
        model.addAttribute("types", types);
        model.addAttribute("searchBlog", searchBlog);

        //搜索到的博客数量
        long blogCount = page.getTotal();

        if(blogCount!=0){
            model.addAttribute("message","查询结果如下：");
        }else{
            model.addAttribute("message","查询失败，没有找到对应的博客！");
        }

        GetOtherData.getNewBlogEstAndAvatar(blogService,userService,model);

        return "admin/blogs";
    }

    //新增
    @PostMapping("/blogs/add")
    public String add(Blog blog, RedirectAttributes redirectAttributes, HttpSession session) {
        //设置发布的管理员的信息
        User user=(User) session.getAttribute("user");
        blog.setUserId(user.getId());
        //设置blog的type
        blog.setType(typeService.getType(blog.getTypeId()));
        //给blog中的List<Tag>赋值
        blog.setTags(tagService.selectTagsByIds(blog.getTagIds()));
        int count=blogService.saveBlog(blog);

        System.out.println("获取前端的信息："+blog);

        if(count>0){
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }else{
            redirectAttributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/blogs";
    }

    @PostMapping("/blogs/update")
    public String update(Blog blog, RedirectAttributes redirectAttributes){
        System.out.println("update前端的数据："+blog);
        int count = blogService.updateBlog(blog);
        if(count>0){
            redirectAttributes.addFlashAttribute("message","修改成功！");
        }else{
            redirectAttributes.addFlashAttribute("message","修改失败！");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        int count = blogService.deleteBlog(id);
        if(count>0){
            redirectAttributes.addFlashAttribute("message","删除成功！");
        }else{
            redirectAttributes.addFlashAttribute("message","删除失败！");
        }
        return "redirect:/admin/blogs";
    }

}
