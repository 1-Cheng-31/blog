package com.cheng.blog.common.aop;

import java.lang.annotation.*;

/**
 * @author cheng
 * @date 2022/5/19 - 14:29
 */
//type 表示可以放到类上面，method 表示可以放到方法上面
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";
}
