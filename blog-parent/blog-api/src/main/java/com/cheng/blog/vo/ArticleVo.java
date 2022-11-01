package com.cheng.blog.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author cheng
 * @date 2022/5/15 - 15:30
 */
@Data
public class ArticleVo {

    //id使用了雪花算法，前端获取有精度损失，无法精准获取，需要加上
    // @JsonSerialize(using = ToStringSerializer.class)
    //@JsonSerialize(using = ToStringSerializer.class)
    //private Long id;

    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;
}
