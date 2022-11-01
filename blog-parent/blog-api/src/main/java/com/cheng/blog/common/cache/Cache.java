package com.cheng.blog.common.cache;

import java.lang.annotation.*;

/**
 * @author cheng
 * @date 2022/5/20 - 9:55
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000;

    //缓存标识key
    String name() default "";

}

