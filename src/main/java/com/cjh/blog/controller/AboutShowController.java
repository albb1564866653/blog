package com.cjh.blog.controller;


import com.cjh.blog.service.BlogService;
import com.cjh.blog.service.UserService;
import com.cjh.blog.util.GetOtherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AboutShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping("/about")
    public String about(Model model) {
        GetOtherData.getNewBlogEstAndAvatar(blogService, userService, model);

        return "about";
    }
}
