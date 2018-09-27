package com.tosim.fileshare.common.config.exception;


import com.tosim.fileshare.common.constants.ErrorCodes;

public class BusinessException extends RuntimeException{

    private ErrorCodes errorCode;

    public BusinessException(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

}
