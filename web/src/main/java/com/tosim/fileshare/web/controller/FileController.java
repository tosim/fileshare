package com.tosim.fileshare.web.controller;

import com.tosim.fileshare.common.config.web.RespJson;
import com.tosim.fileshare.common.config.web.ResultUtil;
import com.tosim.fileshare.common.constants.Constants;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.web.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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

    @ResponseBody
    @GetMapping(value = {"/search"})
    public RespJson search(String keyword,
                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        log.info("fileName: " + keyword + ", page: " + page);
        return ResultUtil.success(fileService.searchFiles(keyword, page, pageSize));
    }
}
