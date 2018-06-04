package com.capacity.ui.main.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuelinghui on 17/9/18.
 */

public class LayoutAreasInfo implements Serializable {

    private int position;

    private List<AppInfo> apps;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<AppInfo> getApps() {
        return apps;
    }

    public void setApps(List<AppInfo> apps) {
        this.apps = apps;
    }
}
