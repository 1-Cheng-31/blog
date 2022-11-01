package com.cheng.blog.controller;

import com.cheng.blog.service.LoginService;
import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 * @date 2022/5/16 - 20:30
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        //sso 单点登录，后期如果把登录注册功能提出去（单独的服务，可以独立提供接口服务）
        return loginService.register(loginParam);
    }
}
