package com.tosim.fileshare.common.util;

public class SFileUtils {

    private static String[] officeSuffix = {"doc", "docx", "xls", "xlsx", "ppt", "pptx"};

    public static boolean isOffice(String suffix) {
        for (String s : officeSuffix) {
            if (s.equals(suffix)) {
                return true;
            }
        }
        return false;
    }

    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void main(String[] args) {
        String fileName = "/a/b/c.doc";
        System.out.println(getSuffix(fileName));
    }
}
