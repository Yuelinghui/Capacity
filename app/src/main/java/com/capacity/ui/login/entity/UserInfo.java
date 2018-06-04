package com.capacity.ui.login.entity;

import com.capacity.ui.main.entity.AppInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class UserInfo implements Serializable {

    /**
     * 用户id
     */
    private String userId;
    /**
     * sesionId
     */
    private String sesionId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户角色
     */
    private String userRole;
    /**
     * 用户选择app
     */
    private List<AppInfo> apps;
    /**
     * 用户上次登录时间
     */
    private long lastLoginTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSesionId() {
        return sesionId;
    }

    public void setSesionId(String sesionId) {
        this.sesionId = sesionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<AppInfo> getApps() {
        return apps;
    }

    public void setApps(List<AppInfo> apps) {
        this.apps = apps;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", sesionId='" + sesionId + '\'' +
                ", userName='" + userName + '\'' +
                ", userRole='" + userRole + '\'' +
                ", apps=" + apps +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
