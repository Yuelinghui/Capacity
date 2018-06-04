package com.capacity.ui.main.entity;

import java.io.Serializable;

/**
 * Created by yuelinghui on 17/9/7.
 */

public class AppSortInfo implements Serializable{

    /**
     * 应用id
     */
    private String id;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用图标
     */
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "AppSortInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
