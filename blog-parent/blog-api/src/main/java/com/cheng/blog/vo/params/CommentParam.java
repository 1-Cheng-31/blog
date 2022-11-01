package com.cheng.blog.vo.params;

import lombok.Data;

/**
 * @author cheng
 * @date 2022/5/17 - 21:45
 */
@Data
public class CommentParam {

    private Long articleId;

    private String content;
    private Long parent;
    private Long toUserId;
}
