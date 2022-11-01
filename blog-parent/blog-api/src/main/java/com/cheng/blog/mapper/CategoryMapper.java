package com.cheng.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheng.blog.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cheng
 * @date 2022/5/17 - 17:08
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
