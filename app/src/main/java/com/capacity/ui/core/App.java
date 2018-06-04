package com.capacity.ui.core;

import android.app.Application;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RunningContext.init(this);
    }
}
