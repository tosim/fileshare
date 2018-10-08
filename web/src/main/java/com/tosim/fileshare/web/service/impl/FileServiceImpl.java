package com.tosim.fileshare.web.service.impl;

import com.tosim.fileshare.common.config.exception.BusinessException;
import com.tosim.fileshare.common.constants.ErrorCodes;
import com.tosim.fileshare.common.domain.FsFile;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.common.mapper.FsFileMapper;
import com.tosim.fileshare.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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
    public List searchFiles(String keyword) {
        return null;
    }
    
}
