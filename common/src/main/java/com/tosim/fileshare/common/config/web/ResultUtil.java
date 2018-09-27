package com.tosim.fileshare.common.config.web;


import com.tosim.fileshare.common.config.exception.BusinessException;
import com.tosim.fileshare.common.config.exception.ParamException;
import com.tosim.fileshare.common.constants.ErrorCodes;
import com.tosim.fileshare.common.dto.RespJson;

public class ResultUtil {

    public static RespJson success(Object object) {
        RespJson result = new RespJson();
        result.setCode(ErrorCodes.SUCCESS.getCode());
        result.setMsg(ErrorCodes.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static RespJson success() {
        return success(null);
    }

    public static RespJson error(String code, String msg) {
        RespJson result = new RespJson();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static RespJson error(ErrorCodes errorCode) {
        return ResultUtil.error(errorCode.getCode(),errorCode.getMsg());
    }

    public static RespJson error(BusinessException be) {
        return ResultUtil.error(be.getErrorCode().getCode(),be.getErrorCode().getMsg());
    }

    public static RespJson error(ParamException pe) {
        return ResultUtil.error(ParamException.code,pe.getMsg());
    }

}
