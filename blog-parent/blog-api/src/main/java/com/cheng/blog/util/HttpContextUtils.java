package com.cheng.blog.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cheng
 * @date 2022/5/19 - 14:42
 */
public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
