package com.qdaily.network;

import com.qdaily.frame.managercenter.MManagerCenter;
import com.qdaily.network.manager.ConfigManager;

/**
 * Created by yuelinghui on 17/8/1.
 */

public class UrlApi {
    private static final String BASE_URL = "http://111.231.92.176:8999/appStore2/admin/";//"http://app3.qdaily.com/";
//    private static final String TEST1_URL = "http://test1.app.qdaily.com/";
//    private static final String TEST2_URL = "http://test2.app.qdaily.com/";

    private static String mCurrentUrl = BASE_URL;

    public static String getCurrentUrl() {

        String url = MManagerCenter.getManager(ConfigManager.class).getUrl();
        if (url == null) {
            return mCurrentUrl;
        } else if (url.equals(mCurrentUrl)) {
            return mCurrentUrl;
        } else {
            return url;
        }

    }

    public static void setCurrentUrl(String currentUrl) {
        mCurrentUrl = currentUrl;
        MManagerCenter.getManager(ConfigManager.class).setUrl(mCurrentUrl);
    }

    public static void initUrl() {
        mCurrentUrl = BASE_URL;
    }
}
