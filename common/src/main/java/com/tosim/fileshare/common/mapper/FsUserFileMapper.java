package com.tosim.fileshare.common.mapper;

import com.tosim.fileshare.common.domain.FsUserFile;
import com.tosim.fileshare.common.config.data.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface FsUserFileMapper extends MyMapper<FsUserFile> {

    @Select("select * from fs_user_file where user_id=#{userId} and file_id=#{fileId}")
    FsUserFile selectByUserIdAndFileId(@Param("userId")String userId, @Param("fileId")String fileId);

}
