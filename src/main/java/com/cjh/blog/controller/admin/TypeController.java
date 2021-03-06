package com.cjh.blog.controller.admin;

import com.cjh.blog.entity.Type;
import com.cjh.blog.service.BlogService;
import com.cjh.blog.service.TypeService;
import com.cjh.blog.service.UserService;
import com.cjh.blog.util.GetOtherData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UserService userService;

    @GetMapping("/types")//  /admin/types?pageNum=?
    public String types(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        //引入PageHelper分页插件
        PageInfo page = getPageInfo_Type(pageNum);

        model.addAttribute("page", page);
        GetOtherData.getNewBlogEstAndAvatar(blogService, userService, model);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());

        GetOtherData.getNewBlogEstAndAvatar(blogService, userService, model);
        return "admin/types-input";
    }

    @GetMapping("/types/editInput/{id}/{pageNum}")
    public String editInput(@PathVariable Long id, @PathVariable Integer pageNum ,Model model,HttpSession session) {
        model.addAttribute("type", typeService.getType(id));
        model.addAttribute("editing", "yes");
        //传入当前修改的元素所在的页码
        session.setAttribute("pageNum_edit",pageNum);

        GetOtherData.getNewBlogEstAndAvatar(blogService, userService, model);
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Type type1 = typeService.getTypeByName(type.getName().trim());
        if (type1 != null) {//name已存在
            //封装错误信息
            bindingResult.rejectValue("name", "nameError", "该分类名称已存在！");
        }

        if (bindingResult.hasErrors()) {

            return "admin/types-input";
        }
        int count = typeService.saveType(type);
        if (count > 0) {
            //保存成功
            redirectAttributes.addFlashAttribute("message", "新增成功！");
        } else {
            //保存失败
            redirectAttributes.addFlashAttribute("message", "新增失败！");

        }

        PageInfo page = getPageInfo_Type(1);
        return "redirect:/admin/types?pageNum="+page.getPages();


    }

    @GetMapping("/types/delete/{id}/{pageNum}")
    public String delete(@PathVariable Long id,@PathVariable Integer pageNum ,RedirectAttributes redirectAttributes) {
        if (blogService.isExistTypeId(id) < 1) {
            //            throw new RuntimeException("博客中不存在该分类，可以删除分类了！！");
            int count = typeService.deleteType(id);
            if (count > 0) {
                redirectAttributes.addFlashAttribute("message", "删除成功！");
            } else {
                redirectAttributes.addFlashAttribute("message", "删除失败！");
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "删除失败，该分类已存在于你的博客中，请在你的博客中将该分类切换再进行删除操作！");
        }

        PageInfo page = getPageInfo_Type(pageNum);
        if(page.getPageNum()>page.getPages()){//当前的页码大于总页码，说明当前页没数据
            pageNum=pageNum-1;
        }

        return "redirect:/admin/types?pageNum="+pageNum;
    }

    @PostMapping("/types/edit")
    public String edit(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes,Model model,HttpSession session) {

        Type type1 = typeService.getTypeByName(type.getName());
        if(type1!=null){//name已存在
            //封装错误信息
            bindingResult.rejectValue("name","nameError","您还没修改或者该分类已存在！");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("editing", "yes");
            return "admin/types-input";
        }

        int count = typeService.updateType(type);
        if (count > 0) {
            //保存成功
            redirectAttributes.addFlashAttribute("message", "更新成功！");
        } else {
            //保存失败
            redirectAttributes.addFlashAttribute("message", "更新失败！");
        }

        Integer pageNum = (Integer) session.getAttribute("pageNum_edit");
        return "redirect:/admin/types?pageNum="+pageNum;
    }

    //获取分类的分页信息
    public PageInfo getPageInfo_Type(Integer pageNum){
        PageHelper.startPage(pageNum, 5);

        List<Type> types = typeService.selectTypes();

        PageInfo<Type> page = new PageInfo<>(types);
        return  page;
    }

}
