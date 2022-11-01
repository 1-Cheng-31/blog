package com.cheng.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.cheng.blog.pojo.SysUser;
import com.cheng.blog.service.LoginService;
import com.cheng.blog.service.TokenService;
import com.cheng.blog.util.JWTUtils;
import com.cheng.blog.vo.ErrorCode;
import com.cheng.blog.vo.LoginUserVo;
import com.cheng.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author cheng
 * @date 2022/5/16 - 19:54
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }

        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);

        if (stringObjectMap == null){
            return null;
        }

        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);

        if(StringUtils.isBlank(userJson)){
            return null;
        }

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }
}
