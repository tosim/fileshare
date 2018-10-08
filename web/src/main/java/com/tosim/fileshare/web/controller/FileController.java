package com.tosim.fileshare.web.controller;

import com.tosim.fileshare.common.config.web.RespJson;
import com.tosim.fileshare.common.config.web.ResultUtil;
import com.tosim.fileshare.common.constants.Constants;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.web.service.FileService;
import lombok.extern.slf4j.Slf4j;
import com.tosim.fileshare.web.service.SessionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;
    @Autowired
    SessionService sessionService;

    @RequiresUser
    @ResponseBody
    @PostMapping("")
    public RespJson upload(MultipartFile file, String fileName, String introduce, Integer point) {
        Subject subject = SecurityUtils.getSubject();
        FsUser loginUser = (FsUser) subject.getSession().getAttribute(Constants.USER_SESSION);
        fileService.upload(file, fileName, introduce, point, loginUser.getUserId());
        return ResultUtil.success();
    }

    @RequiresUser
    @ResponseBody
    @GetMapping(value = {"/search"})
    public RespJson search(String keyword,
                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        log.info("fileName: " + keyword + ", page: " + page);
        return ResultUtil.success(fileService.searchFiles(keyword, page, pageSize));
    }

    @GetMapping("/user")
    public RespJson getFileListByUserId(Integer page, Integer pageSize) {
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute(Constants.USER_ID);
        Map<String, Object> result = fileService.getFileListByUserId(page, pageSize, userId);
        return ResultUtil.success(result);
    }

    @RequiresUser
    @ResponseBody
    @PostMapping("/update/{fileId}")
    public RespJson updateFile(@PathVariable("fileId")String fileId, String fileName, String introduce, Integer point) {
        fileService.updateFile(fileName, introduce, point, fileId);
        return ResultUtil.success();
    }

    @RequiresUser
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(String[] fileIds) throws IOException {
        FsUser loginUser = sessionService.getSessionedUserBySession(SecurityUtils.getSubject().getSession());
        sessionService.removeSessionedUserBySession(SecurityUtils.getSubject().getSession());
        return fileService.download(fileIds, loginUser);
    }
}
