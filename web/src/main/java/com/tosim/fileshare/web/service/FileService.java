package com.tosim.fileshare.web.service;

import com.tosim.fileshare.common.domain.FsFile;
import com.tosim.fileshare.common.domain.FsUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {

    FsFile info(String fileId);

    void upload(MultipartFile file, String fileName, String introduce, Integer point, String ownerUserId);

    ResponseEntity<byte[]> download(String[] fileId, FsUser fsUser) throws IOException;

    boolean delete(String ownerUserId, String fileId);

    Map searchFiles(String keyword, Integer page, Integer pageSize);

    Map<String, Object> getFileListByUserId(Integer page, Integer pageSize, String userId);

    void updateFile(String fileName, String introduce, Integer point, String fileId);

    List<String> getPreviewPngBase64(String fileId);

    Map searchOwnFiles(String ownerUserId, String keyword, Integer page, Integer pageSize, String attr, Integer order);
}
