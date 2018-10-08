package com.tosim.fileshare.common.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "fs_user")
public class FsUser implements Serializable {
    @Id
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    private String gender;

    private String password;

    private Integer points;

    @Column(name = "login_time")
    private Date loginTime;

    @Column(name = "login_ip")
    private Integer loginIp;

    @Column(name = "del_flag")
    private Boolean delFlag;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String introduce;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * @param points
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * @return login_time
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * @return login_ip
     */
    public Integer getLoginIp() {
        return loginIp;
    }

    /**
     * @param loginIp
     */
    public void setLoginIp(Integer loginIp) {
        this.loginIp = loginIp;
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

    /**
     * @return introduce
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * @param introduce
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }
}