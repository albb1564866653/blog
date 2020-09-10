package com.cjh.blog.controller.admin;

import com.cjh.blog.entity.User;
import com.cjh.blog.entity2.CommentManage;
import com.cjh.blog.service.BlogService;
import com.cjh.blog.service.CommentManageService;
import com.cjh.blog.service.UserManageService;
import com.cjh.blog.service.UserService;
import com.cjh.blog.util.GetOtherData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserManageController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserManageService userManageService;

    @RequestMapping("/users")
    public String comments(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        //引入分页
        PageInfo page = getPageInfo_User(pageNum);

        GetOtherData.getNewBlogEstAndAvatar(blogService, userService, model);
        model.addAttribute("page", page);
        return "admin/users";
    }

    @GetMapping("/users/delete/{id}/{pageNum}")
    public String delete(@PathVariable Long id, @PathVariable Integer pageNum , HttpSession session, RedirectAttributes redirectAttributes) {

        int count = userManageService.deleteUser(id);
        if (count > 0) {
                redirectAttributes.addFlashAttribute("message", "删除成功！");
            } else {
                redirectAttributes.addFlashAttribute("message", "删除失败！");
            }
        PageInfo page = getPageInfo_User(pageNum);
        if(page.getPageNum()>page.getPages()){//当前的页码大于总页码，说明当前页没数据
            pageNum=pageNum-1;
        }

        return "redirect:/admin/users?pageNum="+pageNum;
    }

    //获取标签的分页信息
    public PageInfo getPageInfo_User(Integer pageNum){
        PageHelper.startPage(pageNum, 5);
        List<User> userManages = userManageService.selectUsers();
        PageInfo<User> page = new PageInfo<>(userManages);
        return  page;
    }
}
