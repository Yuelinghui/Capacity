package com.capacity.ui.main.ui.detail;

import android.content.Context;
import android.content.Intent;

import com.capacity.ui.core.BaseActivity;

/**
 * Created by yuelinghui on 17/10/30.
 */

public class ImageDetailActivity extends BaseActivity {

    private static final String DETAIL_URL = "DETAIL_URL";


    private String mUrl;
    public static Intent newInstance(Context cotext,String url) {

        Intent intent = new Intent();
        intent.setClass(cotext,ImageDetailActivity.class);
        intent.putExtra(DETAIL_URL,url);
        return intent;
    }
    @Override
    protected void initData() {

        mUrl = getIntent().getStringExtra(DETAIL_URL);

    }

    @Override
    protected void initView() {

    }
}
