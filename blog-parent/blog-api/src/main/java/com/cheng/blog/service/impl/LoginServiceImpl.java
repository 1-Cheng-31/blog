package com.cheng.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.cheng.blog.pojo.SysUser;
import com.cheng.blog.service.LoginService;
import com.cheng.blog.service.SysUserService;
import com.cheng.blog.util.JWTUtils;
import com.cheng.blog.vo.ErrorCode;
import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.params.LoginParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author cheng
 * @date 2022/5/16 - 14:54
 */
@Service
@Transactional//事务操作
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String salt = "cheng!@#";

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1、检查参数是否合法
         * 2、根基用户名和密码去user表查询 是否存在
         * 3、如果不存在  登录失败
         * 4、如果存在，使用jwt 生成token 返回给前端
         * 5、token 放入redis中，redis  token：user信息  设置过期时间
         * （登录认证的时候，先认证token字符串是否合法，去redis中认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        password = DigestUtils.md5Hex(password + salt);

        SysUser sysUser = sysUserService.findUser(account,password);
        if(sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);//redis中删除对应键值
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1、判断参数是否合法
         * 2、判断账户密码是否存在，存在 则返回账户已被注册
         * 3、如果账户不存在，注册用户
         * 4、生成token
         * 5、存入redis 并返回
         * 6、注意：加上事务，一旦中间的任何过程出现问题，注册的用户 需要回滚
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if(StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }

        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), "账号已被注册了");
        }

        sysUser  = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(true); //1 为true
        sysUser.setDeleted(false); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.saveUser(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    /*@Override
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
    }*/
}
