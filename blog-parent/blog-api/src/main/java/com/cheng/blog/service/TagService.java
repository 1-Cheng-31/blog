package com.cheng.blog.service;

import com.cheng.blog.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 设置作者名字
 * @since 2022-05-15
 */
public interface TagService extends IService<Tag> {

    /**
     * 根据文章id 查询标签列表
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 查询所有文章标签
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
