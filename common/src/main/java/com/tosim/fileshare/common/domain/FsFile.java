package com.tosim.fileshare.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "fs_file")
public class FsFile {
    @Id
    private Integer id;

    @Column(name = "file_id")
    private String fileId;

    private String owner;

    @Column(name = "file_name")
    private String fileName;

    private String tags;

    private String suffix;

    private String classification;

    private Integer size;

    private Integer point;

    @Column(name = "reduce_flag")
    private String reduceFlag;

    @Column(name = "storage_uri")
    private String storageUri;

    @Column(name = "private_flag")
    private Boolean privateFlag;

    @Column(name = "del_flag")
    private Boolean delFlag;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return id
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
     * @return file_id
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId
     */
    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    /**
     * @return owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    /**
     * @return file_name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * @return tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags
     */
    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    /**
     * @return suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    /**
     * @return classification
     */
    public String getClassification() {
        return classification;
    }

    /**
     * @param classification
     */
    public void setClassification(String classification) {
        this.classification = classification == null ? null : classification.trim();
    }

    /**
     * @return size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return point
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * @param point
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * @return reduce_flag
     */
    public String getReduceFlag() {
        return reduceFlag;
    }

    /**
     * @param reduceFlag
     */
    public void setReduceFlag(String reduceFlag) {
        this.reduceFlag = reduceFlag == null ? null : reduceFlag.trim();
    }

    /**
     * @return storage_uri
     */
    public String getStorageUri() {
        return storageUri;
    }

    /**
     * @param storageUri
     */
    public void setStorageUri(String storageUri) {
        this.storageUri = storageUri == null ? null : storageUri.trim();
    }

    /**
     * @return private_flag
     */
    public Boolean getPrivateFlag() {
        return privateFlag;
    }

    /**
     * @param privateFlag
     */
    public void setPrivateFlag(Boolean privateFlag) {
        this.privateFlag = privateFlag;
    }

    /**
     * @return del_flag
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}