package com.capacity.ui.main.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuelinghui on 17/9/18.
 */

public class UserLayoutInfo implements Serializable {

    private String layoutId;

    private String userId;

    private String layoutName;

    private String layoutType;

    private List<LayoutAreasInfo> areas;

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public List<LayoutAreasInfo> getAreas() {
        return areas;
    }

    public void setAreas(List<LayoutAreasInfo> areas) {
        this.areas = areas;
    }
}
