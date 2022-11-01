package com.cheng.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cheng.blog.mapper.ArticleBodyMapper;
import com.cheng.blog.mapper.SysUserMapper;
import com.cheng.blog.pojo.ArticleBody;
import com.cheng.blog.pojo.SysUser;
import com.cheng.blog.service.LoginService;
import com.cheng.blog.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheng.blog.service.TokenService;
import com.cheng.blog.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 设置作者名字
 * @since 2022-05-15
 */
@Service
@Lazy//解决循环依赖
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    //@Autowired
    //private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("cheng");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);//复制
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("cheng");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1、token 合法性校验
         *   是否为空，解析是否成功 redis 是否存在
         * 2、如果校验失败，返回错误
         * 3、如果成功，返回对应结果 LoginUserVo
         */
        SysUser sysUser = tokenService.checkToken(token);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }

        LoginUserVo loginUserVo = new LoginUserVo();

        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return Result.success(loginUserVo);

    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");

        return this.sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void saveUser(SysUser sysUser) {
        //保存用户 自增id
        //分布式id默认采用雪花算法
        this.sysUserMapper.insert(sysUser);
    }

    @Override
    public ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

}
