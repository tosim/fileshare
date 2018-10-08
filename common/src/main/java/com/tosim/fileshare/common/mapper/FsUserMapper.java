package com.tosim.fileshare.common.mapper;

import com.tosim.fileshare.common.config.data.MyMapper;
import com.tosim.fileshare.common.domain.FsUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface FsUserMapper extends MyMapper<FsUser> {

    @Select("select * from fs_user where user_name=#{userName}")
    FsUser selectByUserName(String userName);

    @Update("update fs_user set login_time=#{loginTime} where user_id=#{userId}")
    int updateLoginTime(@Param("loginTime")Date loginTime, @Param("userId")String userId);
}