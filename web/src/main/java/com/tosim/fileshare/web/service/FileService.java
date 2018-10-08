package com.tosim.fileshare.web.service;

import com.github.pagehelper.PageInfo;
import com.tosim.fileshare.common.domain.FsUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {

    void upload(MultipartFile file, String fileName, String introduce, Integer point,String ownerUserId);

    ResponseEntity<byte[]> download(String[] fileId, FsUser fsUser) throws IOException;

    Map searchFiles(String keyword, Integer page, Integer pageSize);

    Map<String, Object> getFileListByUserId(Integer page, Integer pageSize, String userId);

    void updateFile(String fileName, String introduce, Integer point, String fileId);
}
