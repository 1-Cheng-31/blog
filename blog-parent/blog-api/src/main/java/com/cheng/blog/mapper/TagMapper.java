package com.cheng.blog.mapper;

import com.cheng.blog.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 设置作者名字
 * @since 2022-05-15
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签id
     * @param limit
     * @return
     */
    List<Long> findHotsTagId(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
