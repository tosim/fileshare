package com.tosim.fileshare.common.constants;

public enum ErrorCodes {

    SUCCESS("A0000","请求成功"),
    PARAM_ERROR("A1001","参数错误"),
    SYSTEM_ERROR("A1002","系统错误"),

    UNAUTHENTICATED("A2001","未登录认证"),
    UNAUTHORIZED("A2002","用户未授权"),

    LOGIN_FAILED("A3001","登录失败"),
    REGISTER_FAILED("A3002","注册失败"),

    DOWNLOAD_FILE_FAILED("A4001","下载文件失败"),
    UPLOAD_FILE_FAILED("A4002","上传文件失败"),

    DELETE_FILE_FAILED("A5001", "删除文件失败"),

    GET_FILE_INFO_FAILED("A6001", "获取文件信息失败");


    private final String code;
    private final String msg;

    ErrorCodes(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
