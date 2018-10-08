package com.tosim.fileshare.common.domain;

import javax.persistence.*;

@Table(name = "fs_user_file")
public class FsUserFile {
    @Id
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "file_id")
    private String fileId;

    /**
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
