package com.cheng.blog.handler;

import com.alibaba.fastjson.JSON;
import com.cheng.blog.pojo.SysUser;
import com.cheng.blog.service.TokenService;
import com.cheng.blog.util.UserThreadLocal;
import com.cheng.blog.vo.ErrorCode;
import com.cheng.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cheng
 * @date 2022/5/17 - 14:21
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    //执行controller方法之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1、需要判断请求的接口路径，是否为 HandlerMethod（controller 方法）
         * 2、判断token 是否为空，如果为空，未登录
         * 3、如果token 不为空，登录验证  TokenService.checkToken()
         * 4、验证成功 放行
         */
        if (!(handler instanceof HandlerMethod)){
            //handler 可能是 访问 静态资源路径（requestResourceHandler）
            return true;
        }

        String token = request.getHeader("Authorization");//获取token

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");


        if (StringUtils.isBlank(token)){//token为空
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));//返回json 数据
            return false;
        }

        SysUser sysUser = tokenService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));//返回json 数据
            return false;
        }
        //登录验证成功，放行
        //想要在controller中，获取用户信息
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 threadLocal 中用完的信息，会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
