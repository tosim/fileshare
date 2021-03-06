package com.tosim.fileshare.web.service.impl;

import com.alibaba.fastjson.JSON;
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
import com.tosim.fileshare.web.util.OfficeToPDF;
import com.tosim.fileshare.web.util.Tojpg.Pdf2Jpg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
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

//    private final

    @Override
    public FsFile info(String fileId) {
        FsFile fsFile = fsFileMapper.selectByFileId(fileId);
        fsFile.setStorageUri(null);
        return fsFile;
    }

    @Transactional
    @Override
    public void upload(MultipartFile file, String fileName, String introduce, Integer point, FsUser ownerUser) {
        try {
            FsFile uploadedFile = new FsFile();
            // 文件生成md5
            uploadedFile.setReduceFlag(DigestUtils.md5DigestAsHex(file.getBytes()));
            uploadedFile.setSuffix(SFileUtils.getSuffix(fileName));
            // 获取重复的文件
            List duplicateFiles = fsFileMapper.select(uploadedFile);

            FsFile duplicateFile;
            String storageUri;
            if (duplicateFiles.size() == 0) {    // 重复的文件不存在则上传
                storageUri = FastDFSUtil.getInstance().upload(file.getBytes(), SFileUtils.getSuffix(file.getOriginalFilename()));
                if (SFileUtils.isOffice(uploadedFile.getSuffix()) || "pdf".equals(uploadedFile.getSuffix())) {
                    log.info("office文件或pdf文件");
                    String dirUrl = "E:\\Workspace\\tmp\\";
                    String fileUrl = dirUrl + ownerUser.getUserName() + "-" + file.getOriginalFilename().split("\\.")[0];
                    log.info("源文件路径（不包括后缀）: {}", fileUrl);
                    File tmpOldFile = new File(fileUrl + "." + uploadedFile.getSuffix());
                    file.transferTo(tmpOldFile);
                    boolean isPdf = false;

                    // .doc .docx .xls .xlsx .ppt .pptx
                    if (SFileUtils.isOffice(uploadedFile.getSuffix())) {
                        OfficeToPDF.office2PDF(fileUrl + "." + uploadedFile.getSuffix(), fileUrl + ".pdf");
                        tmpOldFile.delete();
                        isPdf = true;
                    }

                    // .pdf
                    if ("pdf".equals(uploadedFile.getSuffix()) || isPdf) {
                        File tmpFile = new File(fileUrl + ".pdf");
                        int len = Pdf2Jpg.tranfer(fileUrl + ".pdf", dirUrl);
                        tmpFile.delete();

                        StringBuilder previewStr = new StringBuilder();
                        for (int i = 1; i <= len; i++) {
                            File tmpPng = new File(fileUrl + "_" + i + ".png");
                            String previewUrl = FastDFSUtil.getInstance().upload(
                                    FileUtils.readFileToByteArray(tmpPng),
                                    "png");
                            if (i != 1) {
                                previewStr.append(";");
                            }
                            previewStr.append(previewUrl);
                            tmpPng.delete();

                        }
                        uploadedFile.setPreviewUri(previewStr.toString());

                    }
                }
            } else {    // 已存在的文件不需要重复上传，只需要拿到重复文件的文件路径和预览图片路径
                duplicateFile = (FsFile) duplicateFiles.get(0);
                storageUri = duplicateFile.getStorageUri();
                uploadedFile.setPreviewUri(duplicateFile.getPreviewUri());
            }

            log.info("storage: " + storageUri);
            uploadedFile.setCreateTime(Calendar.getInstance().getTime());
            uploadedFile.setFileId(UUID.randomUUID().toString().replaceAll("-", ""));
            uploadedFile.setFileName(fileName);
            uploadedFile.setOwner(ownerUser.getUserId());
            uploadedFile.setIntroduce(introduce);
            uploadedFile.setPoint(point);
            uploadedFile.setPrivateFlag(false);
            uploadedFile.setSize((int) file.getSize());
            uploadedFile.setStorageUri(storageUri);
            uploadedFile.setUpdateTime(uploadedFile.getCreateTime());

            fsFileMapper.insertSelective(uploadedFile);

            ownerUser.setPoints(ownerUser.getPoints() + 10);
            fsUserMapper.updateByPrimaryKeySelective(ownerUser);

        } catch (IOException e) {
            throw new BusinessException(ErrorCodes.UPLOAD_FILE_FAILED);
        } catch (PDFException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCodes.UPLOAD_FILE_FAILED);
        } catch (PDFSecurityException e) {
            e.printStackTrace();
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        //压缩后的文件
		String resourcesName = "packaged-"+sdf.format(Calendar.getInstance().getTime())+".zip";
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

    @Transactional
    @Override
    public boolean delete(String ownerUserId, String[] fileIds) {
        int len = fsFileMapper.updateByFileIds(ownerUserId, fileIds);
        if (len != fileIds.length) {
            throw new RuntimeException("更新行数与数组行数不一致，更新失败");
        }
        return true;
    }

    @Override
    public Map searchFiles(String keyword, Integer page, Integer pageSize, String attr, Integer order) {
        PageHelper.startPage(page, pageSize);
        List<FsFile> fsFiles = fsFileMapper.selectByFileName(keyword, attr, order);
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
        String[] previewDownloadUriList = previewUri.split(";");
        List<String> base64List = new ArrayList<>();
        for(int i = 0;i < previewDownloadUriList.length;i++)
            base64List.add(Base64.encodeBase64String(FastDFSUtil.getInstance().download(previewDownloadUriList[i])));
        return base64List;
    }

    @Override
    public Map searchOwnFiles(String ownerUserId, String keyword, Integer page, Integer pageSize, String attr, Integer order) {
        PageHelper.startPage(page, pageSize);
        List<FsFile> fsFiles = fsFileMapper.selectByFileNameAndUserId(ownerUserId, keyword, attr, order);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("total", ((Page)fsFiles).getTotal());
        map.put("list", fsFiles);
        return map;
    }

    @Override
    public Map getFileOwnerInfo(String loginUserId, String fileId) {
        FsFile fsFile = fsFileMapper.selectByFileId(fileId);
        FsUser owner = fsUserMapper.selectByUserId(fsFile.getOwner());
        FsUserFile fsUserFile = fsUserFileMapper.selectByUserIdAndFileId(loginUserId, fsFile.getFileId());
        Map<String, Object> ret = new HashMap<>();
        owner.setPassword(null);
        ret.put("owner", owner);
        ret.put("isPayed", (owner.getUserId().equals(loginUserId)) || (fsUserFile != null));
        return ret;
    }
}
