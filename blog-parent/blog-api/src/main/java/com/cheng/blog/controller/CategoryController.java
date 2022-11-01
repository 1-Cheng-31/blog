package com.cheng.blog.controller;

import com.cheng.blog.service.CategoryService;
import com.cheng.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 * @date 2022/5/17 - 22:29
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //所有类别
    @GetMapping
    public Result categories(){
        return categoryService.findAll();
    }

    @GetMapping("/detail")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }

    @GetMapping("/detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }
}
