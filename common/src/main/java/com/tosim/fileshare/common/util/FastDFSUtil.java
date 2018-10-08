package com.tosim.fileshare.common.util;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;

@Slf4j
public class FastDFSUtil {

    private String classpath = FastDFSUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    private String FASTDFS_CLIENT_CONF = classpath + "fastdfs-client.properties";

    private TrackerClient trackerClient = null;

    private TrackerServer trackerServer = null;

    private StorageClient1 storageClient = null;

    private StorageServer storageServer = null;

    private FastDFSUtil() {
        init();
    }

    private void init() {
        try {
            ClientGlobal.init(FASTDFS_CLIENT_CONF);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient1(trackerServer, storageServer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    // 单例内部类
    private static class FastDFSUtilHolder {
        private static final FastDFSUtil INSTANCE = new FastDFSUtil();
    }

    // 单例
    public static FastDFSUtil getInstance() {
        return FastDFSUtilHolder.INSTANCE;
    }

    /**
     *
     * @param content
     * @param suffix
     * @return uploadResults[0]是组名（group），而uploadResults[1]就是组名后面的文件全名
     */
    public String upload(byte[] content, String suffix) {
        return upload(content, suffix, null);
    }

    /**
     *
     * @param content
     * @param suffix
     * @param nameValuePairs
     * @return uploadResults[0]是组名（group），而uploadResults[1]就是组名后面的文件全名
     */
    public String upload(byte[] content, String suffix, NameValuePair[] nameValuePairs) {
        String uploadResults = null;
        try {
            uploadResults = storageClient.upload_file1(content, suffix, nameValuePairs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return uploadResults;
    }

    /**
     *
     * @param fileUrl
     * @return 返回文件内容的byte数组
     */
    public byte[] download(String fileUrl) {
        byte[] content = null;
        try {
            content = storageClient.download_file1(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     *
     * @param fileUrl
     * @return 0表示删除成功，其他表示删除失败
     */
    public int delete(String fileUrl) {
        try {
            return storageClient.delete_file1(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
