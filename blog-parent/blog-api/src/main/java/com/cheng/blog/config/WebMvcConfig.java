package com.cheng.blog.config;

import com.cheng.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cheng
 * @date 2022/5/15 - 12:53
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截test接口，后续可以继续添加
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test","/comments/create/change","/articles/publish");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域设置
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080");
    }
}
