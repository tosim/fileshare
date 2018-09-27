package com.tosim.fileshare.common.config.exception;


import com.tosim.fileshare.common.constants.ErrorCodes;

public class ParamException extends RuntimeException {

    public static final String code = ErrorCodes.PARAM_ERROR.getCode();
    private final String msg;

    public ParamException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
