package com.capacity.ui.main.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuelinghui on 17/9/7.
 */

public class AppInfo implements Serializable {

    public static final int UNKNOWN = 0;
    public static final int LARGE = 1;
    public static final int MIDDLE = 2;
    public static final int SMALL = 3;
    public static final int MINI = 4;
    public static final int WIDE = 5;

    private static final String LARGE_NAME = "_large";
    private static final String MIDDLE_NAME = "_medium";
    private static final String SMALL_NAME = "_small";
    private static final String MINI_NAME = "_mini";
    private static final String WIDE_NAME = "_wide";

    /**
     * 应用id
     */
    private String id;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用版本
     */
    private String version;
    /**
     * 应用类别
     */
    private String category;
    /**
     * 应用场景
     */
    private List<String> appTask;
    /**
     * 应用图标
     */
    private String icon;
    /**
     * 应用描述
     */
    private String description;
    /**
     * 应用缩略图
     */
    private List<String> snapshotHrefs;
    /**
     * 应用更新时间
     */
    private long updateTime;
    /**
     * 应用状态
     */
    private String state;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getAppTask() {
        return appTask;
    }

    public void setAppTask(List<String> appTask) {
        this.appTask = appTask;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSnapshotHrefs() {
        return snapshotHrefs;
    }

    public void setSnapshotHrefs(List<String> snapshotHrefs) {
        this.snapshotHrefs = snapshotHrefs;
    }

    public String getUpdateTime() {
        return String.valueOf(updateTime);
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getThumb(int type) {
        if (type == LARGE) {
            return getThumbWithName(LARGE_NAME);
        } else if (type == MIDDLE) {
            return getThumbWithName(MIDDLE_NAME);
        } else if (type == SMALL) {
            return getThumbWithName(SMALL_NAME);
        } else if (type == MINI) {
            return getThumbWithName(MINI_NAME);
        } else if (type == WIDE){
            return getThumbWithName(WIDE_NAME);
        } else {
            return null;

        }
    }

    public boolean canAddPlanningView(int type) {
        int appType = getAppType();
        if (appType == type) {
            return true;
        }
        return false;
    }

    private int getAppType() {
        if (snapshotHrefs == null || snapshotHrefs.size() == 0 || TextUtils.isEmpty(name)) {
            return UNKNOWN;
        }
        String thumbUrl = snapshotHrefs.get(0);

        if (thumbUrl == null || thumbUrl.length() == 0) {
            return UNKNOWN;
        }

        if (thumbUrl.contains(LARGE_NAME)) {
            return LARGE;
        } else if (thumbUrl.contains(MIDDLE_NAME)) {
            return MIDDLE;
        } else if (thumbUrl.contains(SMALL_NAME)) {
            return SMALL;
        } else if (thumbUrl.contains(MINI_NAME)) {
            return MINI;
        } else if (thumbUrl.contains(WIDE_NAME)) {
            return WIDE;
        } else {
            return UNKNOWN;
        }
    }

    private String getThumbWithName(String name) {
        if (snapshotHrefs == null || snapshotHrefs.size() == 0 || TextUtils.isEmpty(name)) {
            return null;
        }
        String result = null;
        for (String thumb:snapshotHrefs) {
            if (!TextUtils.isEmpty(thumb) && thumb.contains(name)) {
                result = thumb;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", category='" + category + '\'' +
                ", appTask=" + appTask +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", snapshotHrefs=" + snapshotHrefs +
                ", updateTime=" + updateTime +
                ", state='" + state + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
