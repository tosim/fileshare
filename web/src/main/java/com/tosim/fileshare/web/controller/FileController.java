package com.tosim.fileshare.web.controller;

import com.tosim.fileshare.common.config.web.RespJson;
import com.tosim.fileshare.common.config.web.ResultUtil;
import com.tosim.fileshare.common.constants.Constants;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.web.service.FileService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
        Subject subject = SecurityUtils.getSubject();
        FsUser loginUser = (FsUser) subject.getSession().getAttribute(Constants.USER_SESSION);
        fileService.upload(file, fileName, introduce, point, loginUser.getUserId());
        return ResultUtil.success();
    }
}
