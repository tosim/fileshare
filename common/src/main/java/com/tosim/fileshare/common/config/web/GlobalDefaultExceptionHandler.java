package com.tosim.fileshare.common.config.web;

import com.google.common.base.Throwables;
import com.tosim.fileshare.common.config.exception.BusinessException;
import com.tosim.fileshare.common.config.exception.ParamException;
import com.tosim.fileshare.common.constants.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
 　　　　　　　　 NoSuchMethodException,IOException,IndexOutOfBoundsException
 　　　　　　　　 以及springmvc自定义异常等，如下：
 SpringMVC自定义异常对应的status code
 Exception                       HTTP Status Code
 ConversionNotSupportedException         500 (Internal Server Error)
 HttpMessageNotWritableException         500 (Internal Server Error)
 HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 NoSuchRequestHandlingMethodException    404 (Not Found)
 TypeMismatchException                   400 (Bad Request)
 HttpMessageNotReadableException         400 (Bad Request)
 MissingServletRequestParameterException 400 (Bad Request)
 *
 */
@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    //业务异常
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RespJson businessExcepitonHandler(BusinessException businessException) {
            log.error("业务异常：{}", businessException.getMessage());
            return ResultUtil.error(businessException);
    }

    //参数异常
    @ExceptionHandler(ParamException.class)
    @ResponseBody
    public RespJson paramExceptionHandler(ParamException paramException) {
        log.error("参数异常：{}", paramException.getMessage());
        return ResultUtil.error(paramException);
    }

    //shiro 未登录异常
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public RespJson unauthenticatedExceptionHandler(UnauthenticatedException unauthenticatedException) {
        log.error("用户未登录:{}" + unauthenticatedException.getMessage());
        return ResultUtil.error(ErrorCodes.UNAUTHENTICATED);
    }

    //shiro 未授权异常
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public RespJson unauthorizedExceptionHandler(UnauthorizedException unauthorizedException) {
        log.error("用户未登录:{}" + unauthorizedException.getMessage());
        return ResultUtil.error(ErrorCodes.UNAUTHORIZED);
    }

    //其他异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RespJson exceptionHandler(Exception exception) {
        log.error("其他异常：\n{}", Throwables.getStackTraceAsString(exception));
        return ResultUtil.error("E0000","其他异常：" + exception.getMessage());
//        return ResultUtil.error(ErrorCodes.SYSTEM_ERROR);
    }

}
