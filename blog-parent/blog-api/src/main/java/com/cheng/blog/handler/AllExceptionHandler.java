package com.cheng.blog.handler;

import com.cheng.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cheng
 * @date 2022/5/15 - 21:08
 */
@ControllerAdvice//对加了 @Controller 注解的方法进行拦截处理 AOP 的实现
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
