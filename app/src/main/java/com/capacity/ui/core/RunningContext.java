package com.capacity.ui.core;

import android.app.Application;

import com.qdaily.frame.core.RunningEnvironment;
import com.qdaily.frame.managercenter.MManagerCenter;
import com.qdaily.frame.util.LocalDisplay;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class RunningContext extends RunningEnvironment {

    public static void init(Application app) {
        RunningEnvironment.init(app);
        MManagerCenter.init(app);
        LocalDisplay.init(app);
    }
}
