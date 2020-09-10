package com.cjh.blog.controller.admin;

import com.cjh.blog.entity.Tag;
import com.cjh.blog.entity2.BlogQuery;
import com.cjh.blog.entity2.CommentManage;
import com.cjh.blog.service.BlogService;
import com.cjh.blog.service.CommentManageService;
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

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CommentManageController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentManageService commentManageService;

    @RequestMapping("/comments")
    public String comments(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        //引入分页
        PageInfo page = getPageInfo_Comment(pageNum);

        GetOtherData.getNewBlogEstAndAvatar(blogService, userService, model);
        model.addAttribute("page", page);
        return "admin/comments";
    }

    @GetMapping("/comments/delete/{id}/{pageNum}")
    public String delete(@PathVariable Long id,@PathVariable Integer pageNum , RedirectAttributes redirectAttributes) {

        int count = commentManageService.deleteComment(id);

        if (count > 0) {
                redirectAttributes.addFlashAttribute("message", "删除成功！");
            } else {
                redirectAttributes.addFlashAttribute("message", "删除失败！");
            }
        PageInfo page = getPageInfo_Comment(pageNum);
        if(page.getPageNum()>page.getPages()){//当前的页码大于总页码，说明当前页没数据
            pageNum=pageNum-1;
        }

        return "redirect:/admin/comments?pageNum="+pageNum;
    }

    //获取标签的分页信息
    public PageInfo getPageInfo_Comment(Integer pageNum){
        PageHelper.startPage(pageNum, 5);
        List<CommentManage> commentManages = commentManageService.selectComments();
        PageInfo<CommentManage> page = new PageInfo<>(commentManages);
        return  page;
    }
}
