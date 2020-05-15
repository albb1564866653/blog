package com.cjh.blog.controller.user;

import com.cjh.blog.entity.User;
import com.cjh.blog.service.BlogService;
import com.cjh.blog.service.UserService;
import com.cjh.blog.util.Base64Decode;
import com.cjh.blog.util.Base64Encode;
import com.cjh.blog.util.Md5SaltEncode;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    //存储上一个请求的url地址
    private String lastUrl=null;

    @GetMapping("/toLogin")
    public String toLogin(HttpServletRequest request){
        System.out.println("================================进入登录页"+request.getHeader("Referer"));
        lastUrl=request.getHeader("Referer");

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
        return "login";

    }

    @GetMapping("/toRegister")
    public String toRegister(HttpServletRequest request){
        lastUrl=request.getHeader("Referer");
        System.out.println("================================进入注册页"+request.getHeader("Referer"));
        return "register";

    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        String remember, HttpServletRequest request, HttpServletResponse response,
                        RedirectAttributes redirectAttributes,HttpSession session){
        System.out.println("================================执行登录"+lastUrl);
        if(lastUrl.contains("/user/toLogin")){
            lastUrl="/";
        }
        User isExit = userService.selectUser(username);
        if(isExit!=null){//判断是否有这个用户名
            //核对用户名和密码
            User user = userService.checkUser(username, Md5SaltEncode.md5Hash(password,username,3));
            if(user!=null){//成功
                user.setPassword(null);
                session.setAttribute("theUser",user);
                session.setMaxInactiveInterval(-1);
            }else{
                redirectAttributes.addFlashAttribute("message","密码错误");
                return "redirect:/user/toLogin";
            }

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


        }else{
            redirectAttributes.addFlashAttribute("message","用户名不存在");
            return "redirect:/user/toLogin";
        }
        return "redirect:"+lastUrl;


    }

    @GetMapping("/logout")
    public String logout(HttpSession session,HttpServletRequest request){
        session.removeAttribute("theUser");
        lastUrl=request.getHeader("Referer");
        return "redirect:"+lastUrl;
    }

    @PostMapping("/register")
    public String register(User user, @RequestParam("code") String code, RedirectAttributes redirectAttributes,
                           HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        //后端表单校验
        if(!user.getUsername().matches("^[a-zA-Z][a-zA-Z0-9_]{4,15}$")){
            redirectAttributes.addFlashAttribute("message","用户名必须以字母开头，长度在5~16之间，允许字母数字下划线");
            return "redirect:/user/toRegister";
        }
        if(!user.getNickname().matches("(^[a-zA-Z]{2,5}$)|(^[\\u2E80-\\u9FFF]{2,5}$)")){
            redirectAttributes.addFlashAttribute("message","昵称必须是2-5位中文或以字母开头的2-5位英文和数字组合");
            return "redirect:/user/toRegister";
        }
        if(!user.getPassword().matches("^[a-zA-Z]\\w{5,17}$")){
            redirectAttributes.addFlashAttribute("message","密码必须以字母开头，长度在6~18之间，只能包含字符、数字和下划线");
            return "redirect:/user/toRegister";
        }
        if(!user.getEmail().matches("^\\w+@[0-9a-zA-Z]{2,4}\\.[a-zA-Z]{2,3}(\\.[a-zA-Z]{2,3})?$")){
            redirectAttributes.addFlashAttribute("message","邮箱格式错误");
            return "redirect:/user/toRegister";
        }


        //密码加密
        user.setPassword(Md5SaltEncode.md5Hash(user.getPassword(),user.getUsername(),3));
        user.setType(0);
        user.setAvatar("/img/avatar.jpg");

        String sessionCode = (String) session.getAttribute("codeInt");//获取验证码的数字
        //判断用户输入的验证码是否等于服务器的验证码
        if(Objects.equals(sessionCode,code)) {
            //根据用户名查找是否存在此用户名
            User isExits = userService.selectUser(user.getUsername());
            if(isExits==null){//存在
                int count = userService.addUser(user);
                request.setAttribute("lastUrl",lastUrl);
                return "jump";
            }else{
                redirectAttributes.addFlashAttribute("message","用户名已被注册");
                return "redirect:/user/toRegister";
            }

        }else{
            redirectAttributes.addFlashAttribute("message","验证码错误");
            return "redirect:/user/toRegister";
        }


    }


}
