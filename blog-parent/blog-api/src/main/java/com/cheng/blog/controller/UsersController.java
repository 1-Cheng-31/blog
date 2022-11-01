package com.cheng.blog.controller;

import com.cheng.blog.pojo.SysUser;
import com.cheng.blog.service.SysUserService;
import com.cheng.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cheng
 * @date 2022/5/16 - 19:05
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }
}
