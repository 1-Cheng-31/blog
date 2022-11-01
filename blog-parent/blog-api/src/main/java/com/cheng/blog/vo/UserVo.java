package com.cheng.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author cheng
 * @date 2022/5/17 - 20:27
 */
@Data
public class UserVo {

    private String nickname;

    private String avatar;

    //@JsonSerialize(using = ToStringSerializer.class)
    //private Long id;
    private String id;
}
