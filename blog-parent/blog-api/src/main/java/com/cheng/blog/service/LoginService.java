package com.cheng.blog.service;

import com.cheng.blog.pojo.SysUser;
import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.params.LoginParam;

/**
 * @author cheng
 * @date 2022/5/16 - 14:53
 */
public interface LoginService {
    Result login(LoginParam loginParam);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    Result register(LoginParam loginParam);

    //SysUser checkToken(String token);
}
