package com.cheng.blog.pojo;

import lombok.Data;

/**
 * @author cheng
 * @date 2022/5/17 - 16:44
 */
@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
