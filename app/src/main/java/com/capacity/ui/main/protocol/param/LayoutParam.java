package com.capacity.ui.main.protocol.param;

import com.capacity.manager.UserInfoManager;
import com.qdaily.frame.managercenter.MManagerCenter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuelinghui on 17/9/14.
 */

public class LayoutParam implements Serializable {

    /**
     * 布局id
     */
    public String layoutId;
    /**
     * 用户id
     */
    public String userId = MManagerCenter.getManager(UserInfoManager.class).getUserId();
    /**
     * 布局名称
     */
    public String layoutName;
    /**
     * 布局类型
     */
    public String layoutType;
    /**
     * 布局应用信息
     */
    public List<LayoutAreasParam> areas;
}
