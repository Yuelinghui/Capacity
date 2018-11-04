package com.qdaily.network.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.qdaily.frame.managercenter.MManager;

public class ConfigManager extends MManager {
    private static final String PREFERENCES_NAME = "capacity";

    private static final String KEY_URL = "KEY_URL";

    private String url;

    private SharedPreferences sharedPreferences;

    @Override
    public void onManagerInit(Context context) {
        super.onManagerInit(context);

        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
        url = sharedPreferences.getString(KEY_URL, "");
    }


    public void setUrl(String url) {
        this.url = url;
        save();
    }

    public String getUrl() {
        return url;
    }

    private boolean save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_URL,url);
        editor.apply();
        return true;
    }
}
