package com.cheng.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cheng.blog.dos.Archives;
import com.cheng.blog.mapper.ArticleBodyMapper;
import com.cheng.blog.mapper.ArticleMapper;
import com.cheng.blog.mapper.ArticleTagMapper;
import com.cheng.blog.pojo.Article;
import com.cheng.blog.pojo.ArticleBody;
import com.cheng.blog.pojo.ArticleTag;
import com.cheng.blog.pojo.SysUser;
import com.cheng.blog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheng.blog.util.UserThreadLocal;
import com.cheng.blog.vo.ArticleVo;
import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.TagVo;
import com.cheng.blog.vo.params.ArticleParam;
import com.cheng.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 设置作者名字
 * @since 2022-05-15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadPoolService threadPoolService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;


    //显示全部文章
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());

        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));
    }

    /*@Override
    public Result listArticle(PageParams params) {
        *//**
         * 分页查询 article数据库表
         *//*
        Page<Article> page = new Page<>(params.getPage(), params.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (params.getCategoryId() != null){
            //and category_id = #{categoryId}
            queryWrapper.eq(Article::getCategoryId,params.getCategoryId());
        }

        List<Long> articleIdList = new ArrayList<>();

        if (params.getTagId() != null){
            //加入标签 条件查询
            //article 表中，并没有tag字段，一篇文章有多个标签
            //article_tag  article_id 1 : n tag_id
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,params.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTags) {
                articleIdList.add(articleTag.getArticleId());
            }
            if (articleIdList.size() > 0){
                // and id in (1,2,3)
                queryWrapper.in(Article::getId,articleIdList);
            }
        }
        //order by create date desc;
        // 是否置顶排序
        queryWrapper.orderByDesc(Article::getCreateDate, Article::getWeight);

        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //不能直接返回
        List<ArticleVo> articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }*/

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article recode : records) {
            articleVoList.add(copy(recode,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    //@Override //重载
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article recode : records) {
            articleVoList.add(copy(recode,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article,articleVo);

        //转换时间格式
        articleVo.setCreateDate(new DateTime(articleVo.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        //articleVo.setAuthor("cheng");

        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(sysUserService.findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }



    /**
     * 最热文章
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //select id,title from article order by view_counts desc limit 5
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);

        //综合上面三条
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    /**
     * 最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //select id,title from article order by create_date desc limit 5
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);

        //综合上面三条
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    /**
     * 文章归档
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1、根据id查询 文章信息
         * 2、跟经济局bodyId 和 categoryId 去做关联查询
         */
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article,true,true,true,true);

        /**
         * 1、查看了文章，是否新增阅读数
         * 2、查看了文章后，本应该直接返回数据， 这时做了一个更新操作，更新时加写锁，阻塞其他读操作，性能会较低
         * 3、更新 增加了此次接口的 耗时, 如果更新一旦出现问题，不能影响查看文章的操作
         * 4、线程池  可以把更新操作放到线程池执行，和主线程就不相关了
         */
        threadPoolService.updateArticleViewCount(articleMapper,article);

        return Result.success(articleVo);
    }

    /**
     * 文章发布
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {

        //此接口，要加入拦截
        SysUser sysUser = UserThreadLocal.get();

        /**
         * 1、发布文章，目的 构建article对象
         * 2、作者id 当前的登录用户
         * 3、标签  要将标签加入关联列表
         * 4、body 内容存储
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));


        //插入后 会生成一个文章id
        this.articleMapper.insert(article);

        //tag标签
        List<TagVo> tags = articleParam.getTags();
        if (tags != null){
            for(TagVo tag : tags){
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }

        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        Map<String, String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }
}
