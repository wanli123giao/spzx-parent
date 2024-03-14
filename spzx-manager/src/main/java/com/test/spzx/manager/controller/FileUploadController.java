package com.test.spzx.manager.controller;

import com.test.spzx.manager.service.FileUploadService;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
//MultipartFile 是 Spring 框架中的一个接口，用于处理 HTTP 请求中的文件上传。
// 它表示一个上传的文件，可以是一个普通的文件（例如图片、视频等），也可以是一个表单中的文件字段。
    @PostMapping(value = "/fileUpload")
    public Result fileUpload(MultipartFile file) {

       String url= fileUploadService.upload(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }

}
