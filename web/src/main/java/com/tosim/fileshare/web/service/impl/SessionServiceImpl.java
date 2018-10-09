package com.tosim.fileshare.web.service.impl;

import com.tosim.fileshare.common.constants.Constants;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.common.mapper.FsUserMapper;
import com.tosim.fileshare.web.service.SessionService;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SessionServiceImpl implements SessionService {

    @Resource
    FsUserMapper userMapper;

    @Override
    public Boolean setSessionedUserBySession(Session session, FsUser newUser) {
        if(session == null) return false;
        session.setAttribute(Constants.USER_SESSION,newUser);
        return true;
    }

    //如果一个user被修改过了，直接删除这个缓存，下次读取从数据库读取
    @Override
    public Boolean removeSessionedUserBySession(Session session){
        if(session == null) return false;
        session.removeAttribute(Constants.USER_SESSION);
        return true;
    }

    //获取缓存中的user，如果user被更新过，则缓存中不存在user，从数据库获取最新的user
    @Override
    public FsUser getSessionedUserBySession(Session session){
        if(session == null) return null;
        FsUser loginUser = (FsUser) session.getAttribute(Constants.USER_SESSION);
        if(loginUser == null){
            String userId = (String) session.getAttribute(Constants.USER_ID);
            loginUser = userMapper.selectByUserId(userId);
            session.setAttribute(Constants.USER_SESSION,loginUser);
        }
        return loginUser;
    }

    @Override
    public String getSessionedUserIdBySession(Session session){
        if(session == null) return null;
        String userId = (String) session.getAttribute(Constants.USER_ID);
        return userId;
    }

}
