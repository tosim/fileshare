package com.tosim.fileshare.web.controller;

import com.tosim.fileshare.common.config.web.RespJson;
import com.tosim.fileshare.common.config.web.ResultUtil;
import com.tosim.fileshare.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("")
    public RespJson upload(MultipartFile file, String fileName, String introduce, Integer point) {
        fileService.upload(file, fileName, introduce, point);
        return ResultUtil.success();
    }
}
