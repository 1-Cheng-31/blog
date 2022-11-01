package com.cheng.blog.util;

import com.cheng.blog.pojo.SysUser;

/**
 * @author cheng
 * @date 2022/5/17 - 16:00
 */
public class UserThreadLocal {

    private UserThreadLocal(){}

    //线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }

}
