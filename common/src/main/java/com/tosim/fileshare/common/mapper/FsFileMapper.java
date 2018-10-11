package com.tosim.fileshare.common.mapper;

import com.tosim.fileshare.common.config.data.MyMapper;
import com.tosim.fileshare.common.domain.FsFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FsFileMapper extends MyMapper<FsFile> {

    @Select("select * from fs_file where file_id=#{fileId} AND del_flag=0")
    FsFile selectByFileId(String fileId);

    @Select("SELECT * from fs_file where file_name like CONCAT('%', #{fileName}, '%') AND del_flag=0")
    List<FsFile> selectByFileName(String fileName);

    @Select("select * from fs_file where owner=#{userId} AND del_flag=0")
    List<FsFile> selectByUserId(String userId);

    @SelectProvider(type = FsFileMapperProvider.class, method = "selectByFileNameAndUserId")
    List<FsFile> selectByFileNameAndUserId(@Param("userId") String userId, @Param("fileName") String fileName,
                                           @Param("attr") String attr, @Param("order") Integer order);

    class FsFileMapperProvider {
        static Map<String, String> keyValue;

        static {
            keyValue = new HashMap<>(6);
            keyValue.put("file_name", "file_name");
            keyValue.put("size", "size");
            keyValue.put("update_time", "update_time");
        }

        public String selectByFileNameAndUserId(@Param("userId") String userId, @Param("fileName") String fileName,
                                                @Param("attr") String attr, @Param("order") Integer order) {
            return new SQL(){{
                SELECT("*");
                FROM("fs_file");
                WHERE("owner=#{userId} AND file_name like CONCAT('%', #{fileName}, '%') AND del_flag=0");
                if (!keyValue.containsKey(attr)) {
                    ORDER_BY("update_time DESC");
                } else {
                    if (order == 0) {
                        ORDER_BY(attr + " DESC");
                    } else {
                        ORDER_BY(attr + " ASC");
                    }
                }
            }}.toString();
        }
    }
}
