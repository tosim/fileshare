package com.tosim.fileshare.common.constants;

public enum ErrorCodes {

    SUCCESS("A0000","请求成功"),
    PARAM_ERROR("A1001","参数错误"),
    SYSTEM_ERROR("A1002","系统错误"),

    UNAUTHENTICATED("A2001","未登录认证"),
    UNAUTHORIZED("A2002","用户未授权");

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
