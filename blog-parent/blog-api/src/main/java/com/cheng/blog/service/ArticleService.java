package com.cheng.blog.service;

import com.cheng.blog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.params.ArticleParam;
import com.cheng.blog.vo.params.PageParams;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 设置作者名字
 * @since 2022-05-15
 */
public interface ArticleService extends IService<Article> {

    //分页查询 文章列表
    Result listArticle(PageParams params);

    //最热文章
    Result hotArticle(int limit);

    //最新文章
    Result newArticles(int limit);

    //文章归档
    Result listArchives();

    //查看文章
    Result findArticleById(Long articleId);

    //文章发布服务
    Result publish(ArticleParam articleParam);
}
