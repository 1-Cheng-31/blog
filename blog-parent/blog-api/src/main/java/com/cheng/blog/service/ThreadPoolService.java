package com.cheng.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cheng.blog.mapper.ArticleMapper;
import com.cheng.blog.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author cheng
 * @date 2022/5/17 - 17:53
 */
public interface ThreadPoolService {

    void updateArticleViewCount(ArticleMapper articleMapper, Article article);
}
