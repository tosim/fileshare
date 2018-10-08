package com.tosim.fileshare.manager.controller;

import com.tosim.fileshare.common.config.web.RespJson;
import com.tosim.fileshare.common.config.web.ResultUtil;
import com.tosim.fileshare.common.constants.ErrorCodes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminSessionController {

    @ResponseBody
    @PostMapping("/sessions")
    public RespJson login(String accountName, String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(accountName, password);
        try {
            subject.login(token);
            Map<String,Object> data = new HashMap<String,Object>();
            data.put("token",subject.getSession().getId());
            return ResultUtil.success(data);
        } catch (IncorrectCredentialsException e) {
            return ResultUtil.error(ErrorCodes.LOGIN_FAILED);
        } catch (LockedAccountException e) {
            return ResultUtil.error(ErrorCodes.LOGIN_FAILED);
        } catch (AuthenticationException e) {
            return ResultUtil.error(ErrorCodes.LOGIN_FAILED);
        }
    }

    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/sessions/{id}",method = RequestMethod.GET)
    public RespJson logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtil.success();
    }
}
