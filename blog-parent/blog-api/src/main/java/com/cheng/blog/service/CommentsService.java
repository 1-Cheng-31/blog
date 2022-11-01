package com.cheng.blog.service;

import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.params.CommentParam;

/**
 * @author cheng
 * @date 2022/5/17 - 20:05
 */
public interface CommentsService {
    /**
     * 根据文章id 查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 添加评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
