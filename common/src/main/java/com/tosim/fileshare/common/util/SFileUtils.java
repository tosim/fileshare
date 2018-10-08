package com.tosim.fileshare.common.util;

public class SFileUtils {

    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void main(String[] args) {
        String fileName = "/a/b/c.doc";
        System.out.println(getSuffix(fileName));
    }
}
