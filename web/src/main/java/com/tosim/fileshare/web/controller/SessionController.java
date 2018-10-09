package com.tosim.fileshare.web.controller;

import com.tosim.fileshare.common.config.web.RespJson;
import com.tosim.fileshare.common.config.web.ResultUtil;
import com.tosim.fileshare.common.constants.Constants;
import com.tosim.fileshare.common.constants.ErrorCodes;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.web.service.SessionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @PostMapping("")
    public RespJson login(String accountName, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(accountName, password);
        try {
            subject.login(token);
            FsUser loginUser = (FsUser) subject.getSession(false).getAttribute(Constants.USER_SESSION);
            loginUser.setPassword(null);
            return ResultUtil.success(loginUser);
        } catch (IncorrectCredentialsException e) {
            return ResultUtil.error(ErrorCodes.LOGIN_FAILED);
        } catch (LockedAccountException e) {
            return ResultUtil.error(ErrorCodes.LOGIN_FAILED);
        } catch (AuthenticationException e) {
            return ResultUtil.error(ErrorCodes.LOGIN_FAILED);
        }
    }

    @GetMapping("/check")
    public RespJson check() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
            return ResultUtil.success(sessionService.getSessionedUserBySession(subject.getSession()));
        else
            return ResultUtil.error(ErrorCodes.UNAUTHENTICATED);
    }
}
