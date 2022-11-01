package com.cheng.blog.controller;

import com.cheng.blog.pojo.SysUser;
import com.cheng.blog.util.UserThreadLocal;
import com.cheng.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 * @date 2022/5/17 - 15:00
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
