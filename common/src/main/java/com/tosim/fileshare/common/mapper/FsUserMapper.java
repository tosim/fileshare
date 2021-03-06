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

    @Update("update fs_user set points=points+#{addPoint} where user_id=#{userId}")
    int addUserPoint(@Param("addPoint")Integer addPoint, @Param("userId")String userId);

    @Update("update fs_user set points=points-#{subPoint} where user_id=#{userId}")
    int subUserPoint(@Param("subPoint")Integer subPoint, @Param("userId")String userId);

    @Select("select * from fs_user where user_id=#{userId}")
    FsUser selectByUserId(String userId);
}