package com.tosim.fileshare.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tosim.fileshare.common.config.exception.BusinessException;
import com.tosim.fileshare.common.config.exception.ParamException;
import com.tosim.fileshare.common.constants.ErrorCodes;
import com.tosim.fileshare.common.domain.FsFile;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.common.domain.FsUserFile;
import com.tosim.fileshare.common.mapper.FsFileMapper;
import com.tosim.fileshare.common.mapper.FsUserFileMapper;
import com.tosim.fileshare.common.mapper.FsUserMapper;
import com.tosim.fileshare.common.util.FastDFSUtil;
import com.tosim.fileshare.common.util.SFileUtils;
import com.tosim.fileshare.web.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FsFileMapper fsFileMapper;
    @Autowired
    FsUserMapper fsUserMapper;
    @Autowired
    FsUserFileMapper fsUserFileMapper;

    @Override
    public void upload(MultipartFile file, String fileName, String introduce, Integer point, String ownerUserId) {
        try {
            String storageUri = FastDFSUtil.getInstance().upload(file.getBytes(), SFileUtils.getSuffix(file.getOriginalFilename()));
            log.info("路径: " + storageUri);
            FsFile uploadedFile = new FsFile();
            uploadedFile.setCreateTime(Calendar.getInstance().getTime());
            uploadedFile.setFileId(UUID.randomUUID().toString().replaceAll("-", ""));
            uploadedFile.setFileName(fileName);
            uploadedFile.setOwner(ownerUserId);
            uploadedFile.setReduceFlag("todo...");
            uploadedFile.setPoint(point);
            uploadedFile.setSuffix(SFileUtils.getSuffix(fileName));
            uploadedFile.setPrivateFlag(false);
            uploadedFile.setSize((int) file.getSize());
            uploadedFile.setStorageUri(storageUri);
            uploadedFile.setUpdateTime(uploadedFile.getCreateTime());
            fsFileMapper.insertSelective(uploadedFile);
        } catch (IOException e) {
            throw new BusinessException(ErrorCodes.UPLOAD_FILE_FAILED);
        }

    }

    @Override
    public ResponseEntity<byte[]> download(String[] fileIdList, FsUser fsUser) throws IOException{
        int n = fileIdList.length;
        boolean shouldSubPoint[] = new boolean[n];
        FsFile[] fsFileList = new FsFile[fileIdList.length];
        int totalPoint = 0;
        for(int i = 0;i < n;i++){
            fsFileList[i] = fsFileMapper.selectByFileId(fileIdList[i]);
            FsUserFile fsUserFile = fsUserFileMapper.selectByUserIdAndFileId(fsUser.getUserId(), fsFileList[i].getFileId());
            shouldSubPoint[i] = !fsFileList[i].getOwner().equals(fsUser.getUserId()) && fsUserFile == null ? true : false;
            if(shouldSubPoint[i])
                totalPoint = totalPoint + fsFileList[i].getPoint().intValue();
        }
        if (fsUser.getPoints().intValue() < totalPoint)
            throw new BusinessException(ErrorCodes.DOWNLOAD_FILE_FAILED);
        //压缩后的文件
		String resourcesName = "packaged-"+fsUser.getUserId()+".zip";
		String zipTmpFilePath = "D:/" + resourcesName;
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipTmpFilePath));
		for (int i = 0;i < n;i++) {
            zipOut.putNextEntry(new ZipEntry(fsFileList[i].getFileName()));
            zipOut.write(FastDFSUtil.getInstance().download(fsFileList[i].getStorageUri()));
		}
		zipOut.close();
		File file = new File(zipTmpFilePath);
		HttpHeaders headers = new HttpHeaders();
		String filename = new String(resourcesName.getBytes("utf-8"),"iso-8859-1");
		headers.setContentDispositionFormData("attachment", filename);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		for(int i = 0;i < n;i++) {
		    if(shouldSubPoint[i])
		        fsUserMapper.addUserPoint(fsFileList[i].getPoint(), fsFileList[i].getOwner());
        }
        fsUserMapper.subUserPoint(totalPoint,fsUser.getUserId());
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
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

    @Override
    public Map<String, Object> getFileListByUserId(Integer page, Integer pageSize, String userId) {
        PageHelper.startPage(page, pageSize);
        List<FsFile> fsFileList = fsFileMapper.selectByUserId(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("total", ((Page) fsFileList).getTotal());
        result.put("list", fsFileList);
        return result;
    }

    @Transactional
    @Override
    public void updateFile(String fileName, String introduce, Integer point, String fileId) {
        FsFile fsFile = new FsFile();
        Integer id = fsFileMapper.selectByFileId(fileId).getId();
        fsFile.setFileName(fileName);
        fsFile.setIntroduce(introduce);
        fsFile.setPoint(point);
        fsFile.setId(id);
        fsFileMapper.updateByPrimaryKeySelective(fsFile);
    }

    @Override
    public List<String> getPreviewPngBase64(String fileId) {
        FsFile fsFile = fsFileMapper.selectByFileId(fileId);
        String previewUri = fsFile.getPreviewUri();
        if(previewUri == null || previewUri.equals(""))
            throw new ParamException("此文件无预览");
        String[] previewDownloadUriList = previewUri.split(",");
        List<String> base64List = new ArrayList<>();
        for(int i = 0;i < previewDownloadUriList.length;i++)
            base64List.add(Base64.encodeBase64String(FastDFSUtil.getInstance().download(previewDownloadUriList[i])));
        return base64List;
    }
}
