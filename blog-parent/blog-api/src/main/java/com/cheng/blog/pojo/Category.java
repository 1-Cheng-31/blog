package com.cheng.blog.pojo;

import lombok.Data;

/**
 * @author cheng
 * @date 2022/5/17 - 16:45
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
