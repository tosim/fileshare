package com.tosim.fileshare.common.mapper;

import com.tosim.fileshare.common.config.data.MyMapper;
import com.tosim.fileshare.common.domain.FsFile;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FsFileMapper extends MyMapper<FsFile> {

    @Select("select * from fs_file where file_id=#{fileId}")
    FsFile selectByFileId(String fileId);

    @Select("SELECT * from fs_file where file_name like CONCAT('%', #{fileName}, '%')")
    List<FsFile> selectByFileName(String fileName);

}
