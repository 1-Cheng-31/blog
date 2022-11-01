package com.cheng.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cheng.blog.mapper.ArticleMapper;
import com.cheng.blog.pojo.Article;
import com.cheng.blog.service.ThreadPoolService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @date 2022/5/22 - 10:41
 */
@Component
public class ThreadPoolServiceImpl implements ThreadPoolService {

    //期望次线程池，不会影响原有主线程
    @Async("taskExecutor")//开启多线程
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        Integer viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);

        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //设置一个 为了在多线程的环境下，线程安全
        updateWrapper.eq(Article::getViewCounts,viewCounts);

        //update article set view_count=100 where view_count=99 and id=11
        articleMapper.update(articleUpdate,updateWrapper);

        try {
            Thread.sleep(5000);//睡眠5秒
            System.out.println("更新完成了。。。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
