package com.cjh.blog.service.impl;

import com.cjh.blog.NotFoundException;
import com.cjh.blog.dao.BlogRepository;
import com.cjh.blog.dao.TagRepository;
import com.cjh.blog.dao.TypeRepository;
import com.cjh.blog.entity.Tag;
import com.cjh.blog.entity.Type;
import com.cjh.blog.entity2.BlogAndTag2;
import com.cjh.blog.service.TagService;
import com.cjh.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {


    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> selectTags() {
        return tagRepository.selectTags();
    }

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagRepository.saveTag(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getTag(id);
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        Tag t = tagRepository.getTag(tag.getId());
        if(t==null){
            throw new NotFoundException("不存在该标签！");
        }
        return tagRepository.updateTag(tag);
    }

    @Transactional
    @Override
    public int deleteTag(Long id) {
        //获取标签的id对应的博客表的id
        Long blogId = tagRepository.selectBlogId(id);
        blogRepository.deleteBlogAndTag2(id);
        //获取其他存在于t_blog_tags表的tag_id值
        List<Long> tagIds = tagRepository.selectTagId(blogId);

        if(blogId!=null){//blog_tags表有关联的数据

            StringBuffer sb=new StringBuffer();
            for(Long temp:tagIds){
                sb.append(temp+",");
            }
            //sb：1,2,3,
            sb.deleteCharAt(sb.length()-1);//把尾部的","去除
            //更新t_blog表的tag_ids
            tagRepository.updateTagIds(new BlogAndTag2(sb.toString(),blogId));

        }


        return tagRepository.deleteTag(id);
    }

    @Override
    public List<Tag> selectTagsByIds(String ids) {//1,2,3
        List<Tag> tags=new ArrayList<>();
        List<Long> ids2 = convertToList(ids);//[1,2,3]
        for(Long temp:ids2){
            tags.add(tagRepository.getTag(temp));
        }
        return tags;
    }

    public static List<Long> convertToList(String ids) {//将1,2,3转换成[1,2,3]
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public Tag getTagByName(String name) {

        return tagRepository.getTagByName(name);
    }

    @Override
    public List<Tag> selectIndexTags() {
        return tagRepository.selectIndexTags();
    }

    @Override
    public List<Tag> selectIndexTags2() {
        return tagRepository.selectIndexTags2();
    }
}
