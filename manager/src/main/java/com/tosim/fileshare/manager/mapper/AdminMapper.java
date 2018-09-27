package com.tosim.fileshare.manager.mapper;

import com.tosim.fileshare.common.domain.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.tosim.fileshare.common.config.data.MyMapper;

public interface AdminMapper extends MyMapper<Admin> {

    @Select("select * from admin where login_name=#{loginName}")
    Admin selectByLoginName(String loginName);

    @Update("update admin set token=#{newToken} where login_name=#{loginName}")
    Integer updateTokenByLoginName(@Param("newToken") String newToken, @Param("loginName") String loginName);
}