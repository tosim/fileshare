package com.tosim.fileshare.web.service.impl;

import com.tosim.fileshare.common.config.exception.BusinessException;
import com.tosim.fileshare.common.constants.ErrorCodes;
import com.tosim.fileshare.common.domain.FsUser;
import com.tosim.fileshare.common.mapper.FsUserMapper;
import com.tosim.fileshare.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    FsUserMapper fsUserMapper;

    @Override
    public void register(String accountName, String password, String confirmPassword, String email) {
        FsUser exsitUser = fsUserMapper.selectByUserName(accountName);
        if(exsitUser != null || !password.equals(confirmPassword))
            throw new BusinessException(ErrorCodes.REGISTER_FAILED);
        FsUser registUser = new FsUser();
        registUser.setUserName(accountName);
        registUser.setPassword(password);
        registUser.setCreateTime(Calendar.getInstance().getTime());
        registUser.setUpdateTime(registUser.getCreateTime());
        registUser.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
        registUser.setLoginTime(registUser.getCreateTime());
        registUser.setLoginIp(0);
        fsUserMapper.insertSelective(registUser);
    }
}
