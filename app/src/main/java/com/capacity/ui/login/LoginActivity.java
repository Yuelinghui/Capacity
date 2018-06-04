package com.capacity.ui.login;

import android.content.Context;
import android.content.Intent;

import com.capacity.ui.R;
import com.capacity.ui.core.BaseActivity;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class LoginActivity extends BaseActivity {

    public static Intent newInstance(Context context) {

        Intent intent = new Intent();
        intent.setClass(context,LoginActivity.class);
        return intent;
    }
    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.app_name));
        setLeftHide(true);
        startFirstFragment(new LoginFragment());
    }
}
