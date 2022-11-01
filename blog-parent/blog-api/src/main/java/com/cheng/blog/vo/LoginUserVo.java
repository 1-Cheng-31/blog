package com.cheng.blog.vo;

import lombok.Data;

/**
 * @author cheng
 * @date 2022/5/16 - 19:23
 */
@Data
public class LoginUserVo {

    //private Long id;
    private String id;
    private String account;
    private String nickname;
    private String avatar;
}
