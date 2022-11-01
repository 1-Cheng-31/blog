package com.cheng.blog.service;

import com.cheng.blog.vo.CategoryVo;
import com.cheng.blog.vo.Result;

/**
 * @author cheng
 * @date 2022/5/17 - 17:06
 */
public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoriesDetailById(Long id);
}
