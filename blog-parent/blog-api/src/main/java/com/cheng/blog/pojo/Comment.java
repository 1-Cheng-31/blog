package com.cheng.blog.pojo;

import lombok.Data;

/**
 * @author cheng
 * @date 2022/5/17 - 20:00
 */
@Data
public class Comment {
    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}
