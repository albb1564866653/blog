package com.cjh.blog.controller.admin;

import com.cjh.blog.entity.User;
import com.cjh.blog.entity2.BlogQuery;
import com.cjh.blog.service.BlogService;
import com.cjh.blog.service.UserService;
import com.cjh.blog.util.Base64Decode;
import com.cjh.blog.util.Base64Encode;
import com.cjh.blog.util.Md5Encode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.text.normalizer.NormalizerBase;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie temp:cookies){
                if (temp.getName().equals("rememberInfo")) {
                    String[] loginInfo = temp.getValue().split("-");
                    request.setAttribute("userName",loginInfo[0]);
                    request.setAttribute("password",new String(Base64Decode.base64Decode(loginInfo[1])));
                }

            }
        }

        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, String remember, HttpServletResponse response,
                        RedirectAttributes redirectAttributes, HttpServletRequest request,
                        Model model){

        if(remember!=null){//记住密码
            System.out.println(request.getContextPath());
            //将密码进行 base64加密再放进cookie
            Cookie cookie=new Cookie("rememberInfo",username+"-"+ Base64Encode.base64Encode(password.getBytes()));
            cookie.setMaxAge(60*60*24*31);//一个月
            response.addCookie(cookie);

        }else{
            Cookie cookie=new Cookie("rememberInfo","");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.创建令牌，封装用户数据
        UsernamePasswordToken token=new UsernamePasswordToken(username, Md5Encode.md5Encode(password));
        try {
            //3.登录
            System.out.println("subject-----------"+subject);
            subject.login(token);

            //最新博客信息
            List<BlogQuery> newBlogs = blogService.selectNewBlogs();
            model.addAttribute("newBlogs", newBlogs);
            //获取管理员信息，将信息存入session
            User user = userService.checkUser(username, Md5Encode.md5Encode(password));
            user.setPassword(null);
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(-1);
        }  catch (UnknownAccountException e) {
            redirectAttributes.addFlashAttribute("message","用户名错误！");
//            e.printStackTrace();
            return "redirect:/admin";
        } catch (IncorrectCredentialsException e) {
            redirectAttributes.addFlashAttribute("message","密码错误！");
//            e.printStackTrace();
            return "redirect:/admin";
        }	catch (AuthenticationException e) {
//            e.printStackTrace();
            return "redirect:/admin";
        }
        return "admin/index";

//        User user = userService.checkUser(username, Md5Encode.md5Encode(password));
//
//        if(user!=null){
//            user.setPassword(null);
//            session.setAttribute("user",user);
//            session.setMaxInactiveInterval(-1);
//
//            //最新博客信息
//            List<BlogQuery> newBlogs = blogService.selectNewBlogs();
//            model.addAttribute("newBlogs", newBlogs);
//
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            return "admin/index";
//        }else{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            redirectAttributes.addFlashAttribute("message","用户名或密码错误！");
//            return "redirect:/admin";
//        }

    }

    @GetMapping("/logout")
    public String logout(){
        System.out.println("==============执行注销============");
        Subject subject = SecurityUtils.getSubject();
        //注销，会将session也清除
        subject.logout();
        return "redirect:/admin";
    }
}
