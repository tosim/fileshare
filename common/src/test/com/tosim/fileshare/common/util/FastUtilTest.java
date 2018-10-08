package com.tosim.fileshare.common.util;

import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.io.*;

public class FastUtilTest {

    @Test
    public void upload() throws IOException {
        File file = new File("D:\\nginx.conf");
        byte[] content = new byte[(int)file.length()];
        new FileInputStream(file).read(content);

        System.out.println(FastDFSUtil.getInstance().upload(content, "conf"));
    }

    @Test
    public void delete() {
        System.out.println(FastDFSUtil.getInstance().delete("group1/M00/00/00/wKiJZ1u2HHWAAECHAAAT_QFZvSc38.conf"));
    }

    @Test
    public void download() {
        File file = new File("D:\\1.png");
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(FastDFSUtil.getInstance().download("group2/M00/00/00/wKiJaFu7dCiAClK1AAI6wWLvVlE656.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test() throws IOException {
        File file = new File("C:\\Users\\SqList\\Documents\\Tencent Files\\574146616\\FileRecv\\2015级学分绩点.xls");
        System.out.println(DigestUtils.md5DigestAsHex(new FileInputStream(file)));
    }
}
