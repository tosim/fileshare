package com.tosim.fileshare.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tosim.fileshare.common.config.exception.BusinessException;
import com.tosim.fileshare.common.constants.ErrorCodes;
import com.tosim.fileshare.common.domain.FsFile;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.common.mapper.FsFileMapper;
import com.tosim.fileshare.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FsFileMapper fsFileMapper;

    @Override
    public void upload(MultipartFile file, String fileName, String introduce, Integer point,String ownerUserId) {
        //TODO: 上传文件到服务器
        //FileUtil.uploadFile(file);
        FsFile uploadedFile = new FsFile();
        uploadedFile.setCreateTime(Calendar.getInstance().getTime());
        uploadedFile.setFileId(UUID.randomUUID().toString().replaceAll("-",""));
        uploadedFile.setFileName(fileName);
        uploadedFile.setOwner(ownerUserId);
        uploadedFile.setPrivateFlag(false);
        uploadedFile.setSize((int)file.getSize());
    }

    @Override
    public void download(String fileId, FsUser fsUser) {
        FsFile fsFile = fsFileMapper.selectByFileId(fileId);
        if(fsFile == null || fsUser.getPoints().compareTo(fsFile.getPoint()) < 0)
            throw new BusinessException(ErrorCodes.DOWNLOAD_FILE_FAILED);
        //TODO: downloadFile
    }

    @Override
    public Map searchFiles(String keyword, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<FsFile> fsFiles = fsFileMapper.selectByFileName(keyword);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("total", ((Page)fsFiles).getTotal());
        map.put("list", fsFiles);
        return map;
    }

}
