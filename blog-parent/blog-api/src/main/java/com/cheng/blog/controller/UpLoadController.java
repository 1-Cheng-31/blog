package com.cheng.blog.controller;

import com.cheng.blog.util.QiniuUtils;
import com.cheng.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author cheng
 * @date 2022/5/19 - 14:58
 */
@RestController
@RequestMapping("/upload")
public class UpLoadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file){
        //原始文件名称 比如：aa.png
        String originalFilename = file.getOriginalFilename();
        //唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." +
                StringUtils.substringAfterLast(originalFilename, ".");

        //上传位置
        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return Result.success(QiniuUtils.url + "/" + fileName);
        }
        return Result.fail(20001,"上传失败");
    }
}
