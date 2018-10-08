package com.tosim.fileshare.web.service;

import com.github.pagehelper.PageInfo;
import com.tosim.fileshare.common.domain.FsUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {

    void upload(MultipartFile file, String fileName, String introduce, Integer point,String ownerUserId);

    void download(String fileId, FsUser fsUser);

    Map searchFiles(String keyword, Integer page, Integer pageSize);

}
