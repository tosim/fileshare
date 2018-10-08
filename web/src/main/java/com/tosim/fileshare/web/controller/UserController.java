package com.tosim.fileshare.web.controller;

import com.tosim.fileshare.common.config.web.RespJson;
import com.tosim.fileshare.common.config.web.ResultUtil;
import com.tosim.fileshare.common.constants.Constants;
import com.tosim.fileshare.common.constants.ErrorCodes;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.web.service.SessionService;
import com.tosim.fileshare.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    SessionService sessionService;

    @PostMapping("")
    public RespJson register(String accountName, String password, String confirmPassword, String email){
        userService.register(accountName, password, confirmPassword, email);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(accountName, password);
        try {
            subject.login(token);
            FsUser loginUser = (FsUser) subject.getSession(false).getAttribute(Constants.USER_SESSION);
            loginUser.setPassword(null);
            return ResultUtil.success(loginUser);
        } catch (Exception e) {
            return ResultUtil.error(ErrorCodes.REGISTER_FAILED);
        }
    }

    @PostMapping("/update")
    public RespJson updateUser(String gender, String introduce) {
        FsUser loginUser = (FsUser) SecurityUtils.getSubject().getSession().getAttribute(Constants.USER_SESSION);
        userService.updateUser(gender, introduce, loginUser.getId());
        sessionService.removeSessionedUserBySession(SecurityUtils.getSubject().getSession());
        return ResultUtil.success();
    }
}
