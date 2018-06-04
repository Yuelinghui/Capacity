package com.capacity.ui.main.protocol.param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuelinghui on 17/9/13.
 */

public class LayoutAreasParam implements Serializable {
    /**
     * 布局位置
     */
    private int position;
    /**
     * 该位置下放置应用的id
     */
    private List<String> appIds;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<String> getAppIds() {
        return appIds;
    }

    public void setAppIds(List<String> appIds) {
        this.appIds = appIds;
    }

    @Override
    public String toString() {
        return "LayoutAreasParam{" +
                "position=" + position +
                ", appIds=" + appIds +
                '}';
    }
}
