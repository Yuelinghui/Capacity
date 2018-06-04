package com.capacity.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.capacity.ui.login.entity.UserInfo;
import com.capacity.ui.main.entity.AppInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qdaily.frame.managercenter.MManager;

import java.util.List;

/**
 * Created by yuelinghui on 17/9/7.
 */

public class UserInfoManager extends MManager {

    private static final String PREFERENCES_NAME = "capacity";

    private static final String KEY_USER_ID = "KEY_USER_ID";

    private static final String KEY_USER_SESSION_ID = "KEY_USER_SESSION_ID";

    private static final String KEY_USER_NAME = "KEY_USER_NAME";

    private static final String KEY_USER_ROLE = "KEY_USER_ROLE";

    private static final String KEY_USER_APP = "KEY_USER_APP";

    private static final String KEY_USER_LOGIN_TIME = "KEY_USER_LOGIN_TIME";

    private SharedPreferences sharedPreferences;

    private Gson gson;

    private String userId;

    private String sessionId;

    private String userName;

    private String userRole;

    private String userAppList;

    private String loginTime;


    @Override
    public void onManagerInit(Context context) {
        super.onManagerInit(context);

        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
        gson = new Gson();

        userId = sharedPreferences.getString(KEY_USER_ID, "");
        sessionId = sharedPreferences.getString(KEY_USER_SESSION_ID, "");
        userName = sharedPreferences.getString(KEY_USER_NAME, "");
        userRole = sharedPreferences.getString(KEY_USER_ROLE, "");
        userAppList = sharedPreferences.getString(KEY_USER_APP, "");
        loginTime = sharedPreferences.getString(KEY_USER_LOGIN_TIME,"");
    }

    public boolean updateUserInfo(UserInfo userInfo) {
        userId = userInfo.getUserId();
        userName = userInfo.getUserName();
        userRole = userInfo.getUserRole();
        sessionId = userInfo.getSesionId();
        userAppList = gson.toJson(userInfo.getApps());
        loginTime = String.valueOf(userInfo.getLastLoginTime());
        return save();
    }

    public String getUserName() {
        return userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void clearUserApp() {
        userAppList = null;
        save();
    }
    public List<AppInfo> getUserApp() {
        if (TextUtils.isEmpty(userAppList)) {
            return null;
        }
        List<AppInfo> reuslt = gson.fromJson(userAppList, new TypeToken<List<AppInfo>>() {
        }.getType());
        return reuslt;
    }

    private boolean save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_ROLE, userRole);
        editor.putString(KEY_USER_SESSION_ID, sessionId);
        editor.putString(KEY_USER_APP, userAppList);
        editor.putString(KEY_USER_LOGIN_TIME,loginTime);
        editor.apply();
        return true;
    }
}
