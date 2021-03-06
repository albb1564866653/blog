package com.cjh.blog.controller.admin;

import com.cjh.blog.entity.Tag;
import com.cjh.blog.entity.Type;
import com.cjh.blog.entity2.BlogQuery;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagsController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    @GetMapping("/tags")
    public String tags(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        //引入PageHelper分页插件
        PageInfo page = getPageInfo_Tag(pageNum);
        model.addAttribute("page", page);

        GetOtherData.getNewBlogEstAndAvatar(blogService, userService, model);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());

        GetOtherData.getNewBlogEstAndAvatar(blogService, userService, model);
        return "admin/tags-input";
    }

    @GetMapping("/tags/editInput/{id}/{pageNum}")
    public String editInput(@PathVariable Long id, @PathVariable Integer pageNum , Model model, HttpSession session) {
        model.addAttribute("tag", tagService.getTag(id));
        model.addAttribute("editing", "yes");
        //传入当前修改的元素所在的页码
        session.setAttribute("pageNum_edit",pageNum);

        GetOtherData.getNewBlogEstAndAvatar(blogService, userService, model);
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes,Model model) {
        Tag tag1 = tagService.getTagByName(tag.getName().trim());
        if (tag1 != null) {//name已存在
            //封装错误信息
            bindingResult.rejectValue("name", "nameError", "该标签名称已存在！");
        }
        if (bindingResult.hasErrors()) {
            return "admin/tags-input";
        }
        int count = tagService.saveTag(tag);
        if (count > 0) {
            //保存成功
            redirectAttributes.addFlashAttribute("message", "新增成功！");
        } else {
            //保存失败
            redirectAttributes.addFlashAttribute("message", "新增失败！");

        }

        PageInfo page = getPageInfo_Tag(1);

        return "redirect:/admin/tags?pageNum="+page.getPages();


    }

    @GetMapping("/tags/delete/{id}/{pageNum}")
    public String delete(@PathVariable Long id,@PathVariable Integer pageNum , RedirectAttributes redirectAttributes) {

        if (blogService.isExistTagId("%" + id + "%") < 1) {
//            throw new RuntimeException("博客中不存在该标签，可以删除标签了！！");
            int count = tagService.deleteTag(id);

            if (count > 0) {
                redirectAttributes.addFlashAttribute("message", "删除成功！");
            } else {
                redirectAttributes.addFlashAttribute("message", "删除失败！");
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "删除失败，该标签已存在于你的博客中，请在你的博客中将该标签移除再进行删除操作！");
        }

        PageInfo page = getPageInfo_Tag(pageNum);
        if(page.getPageNum()>page.getPages()){//当前的页码大于总页码，说明当前页没数据
            pageNum=pageNum-1;
        }

        return "redirect:/admin/tags?pageNum="+pageNum;
    }

    @PostMapping("/tags/edit")
    public String edit(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes,Model model,HttpSession session) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1!=null){//name已存在
            //封装错误信息
            bindingResult.rejectValue("name","nameError","您还没修改或者该标签已存在！");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("editing", "yes");
            return "admin/tags-input";
        }
        int count = tagService.updateTag(tag);
        if (count > 0) {
            //保存成功
            redirectAttributes.addFlashAttribute("message", "更新成功！");
        } else {
            //保存失败
            redirectAttributes.addFlashAttribute("message", "更新失败！");
        }
        Integer pageNum = (Integer) session.getAttribute("pageNum_edit");
        return "redirect:/admin/tags?pageNum="+pageNum;
    }

    //获取标签的分页信息
    public PageInfo getPageInfo_Tag(Integer pageNum){
        PageHelper.startPage(pageNum, 5);

        List<Tag> tags = tagService.selectTags();

        PageInfo<Tag> page = new PageInfo<>(tags);
        return  page;
    }

}
