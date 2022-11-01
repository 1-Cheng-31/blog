package com.cheng.blog.service;

import com.cheng.blog.pojo.SysUser;
import com.cheng.blog.vo.Result;

/**
 * @author cheng
 * @date 2022/5/16 - 19:53
 */
public interface TokenService {
    SysUser checkToken(String token);
}
