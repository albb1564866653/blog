package com.cjh.blog.controller.admin;

import com.cjh.blog.entity.Comment;
import com.cjh.blog.entity.User;
import com.cjh.blog.service.CommentService;
import com.cjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/adminInfo")
    public String adminInfo(HttpSession session, Model model){
        User user=(User)session.getAttribute("user");
        User userInfo = userService.selectUser(user.getUsername());
        model.addAttribute("userInfo",userInfo);
        return "admin/adminInfo";
    }

    @PostMapping("/saveInfo")
    public String saveInfo(User user,RedirectAttributes redirectAttributes,HttpSession session){
        System.out.println("user对象："+user);

        int count = userService.updateUserInfo(user);
        //更新评论模块中管理员的信息
        int result=commentService.updateAdminComment(new Comment(user.getNickname(),user.getEmail()));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }
}
