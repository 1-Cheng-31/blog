package com.cheng.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author cheng
 * @date 2022/5/17 - 20:25
 */
@Data
public class CommentVo {

    //序列化，防止前端精度损失，把id转换为string
    //@JsonSerialize(using = ToStringSerializer.class)
    //private Long id;

    private String id;

    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}
