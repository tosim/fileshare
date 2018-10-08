package com.tosim.fileshare.common.mapper;

import com.tosim.fileshare.common.config.data.MyMapper;
import com.tosim.fileshare.common.domain.FsFile;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FsFileMapper extends MyMapper<FsFile> {

    @Select("select * from fs_file where file_id=#{fileId}")
    FsFile selectByFileId(String fileId);

    @Select("select * from fs_file where owner=#{userId}")
    List<FsFile> selectByUserId(String userId);

}