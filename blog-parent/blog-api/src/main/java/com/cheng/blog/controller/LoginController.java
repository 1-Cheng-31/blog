package com.cheng.blog.controller;

import com.cheng.blog.service.LoginService;
import com.cheng.blog.service.SysUserService;
import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 * @date 2022/5/16 - 14:50
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
