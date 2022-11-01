package com.cheng.blog.service;

import com.cheng.blog.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheng.blog.vo.ArticleBodyVo;
import com.cheng.blog.vo.Result;
import com.cheng.blog.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 设置作者名字
 * @since 2022-05-15
 */
public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账号 查询用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    void saveUser(SysUser sysUser);

    ArticleBodyVo findArticleBodyById(Long bodyId);
}
