package com.tosim.fileshare.common.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class Admin implements Serializable {
    /**
     * 管理员id
     */
    @Id
    @Column(name = "admin_id")
    private Integer adminId;

    /**
     * 登录用户名
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 登录密码
     */
    private String passwd;

    /**
     * token
     */
    private String token;

    /**
     * 获取管理员id
     *
     * @return admin_id - 管理员id
     */
    public Integer getAdminId() {
        return adminId;
    }

    /**
     * 设置管理员id
     *
     * @param adminId 管理员id
     */
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    /**
     * 获取登录用户名
     *
     * @return login_name - 登录用户名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录用户名
     *
     * @param loginName 登录用户名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * 获取登录密码
     *
     * @return passwd - 登录密码
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * 设置登录密码
     *
     * @param passwd 登录密码
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}