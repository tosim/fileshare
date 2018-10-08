package com.tosim.fileshare.web.service;

import com.tosim.fileshare.common.domain.FsUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void upload(MultipartFile file, String fileName, String introduce, Integer point,String ownerUserId);

    void download(String fileId, FsUser fsUser);

    List searchFiles(String keyword);

}
