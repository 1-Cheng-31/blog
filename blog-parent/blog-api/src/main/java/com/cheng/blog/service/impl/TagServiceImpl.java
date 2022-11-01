package com.cheng.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cheng.blog.mapper.TagMapper;
import com.cheng.blog.pojo.Tag;
import com.cheng.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 设置作者名字
 * @since 2022-05-15
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatisplus 无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag: tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }




    @Override
    public Result hots(int limit) {
        /**
         * 1、标签所拥有的文章数量最多--最热标签
         * 2、查询  根据tag_id 分组计数，从小到大排列  取到limit 个
         */
        List<Long> tagIds = tagMapper.findHotsTagId(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        //需求的是 tagId 和 tagName  Tag对象
        //select * from ms_tag where id in {1,2,3,4};
        List<Tag> tagList =  tagMapper.findTagsByTagIds(tagIds);

        return Result.success(tagList);//success方法的参数是数据
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findAllDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }
}
