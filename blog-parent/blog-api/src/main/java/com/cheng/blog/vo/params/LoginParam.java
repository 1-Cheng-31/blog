package com.cheng.blog.vo.params;

import lombok.Data;

/**
 * @author cheng
 * @date 2022/5/16 - 14:56
 */
@Data
public class LoginParam {

    private String account;
    private String password;
    private String nickname;
}
