package com.cheng.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheng.blog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cheng
 * @date 2022/5/17 - 20:08
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
