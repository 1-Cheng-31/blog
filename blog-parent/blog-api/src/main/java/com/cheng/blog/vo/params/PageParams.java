package com.cheng.blog.vo.params;

import lombok.Data;

/**
 * @author cheng
 * @date 2022/5/15 - 14:24
 */
@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0" + this.month;
        }
        return this.month;
    }
}
