package com.capacity.ui.login;

import android.text.TextUtils;
import android.widget.EditText;

import com.capacity.manager.UserInfoManager;
import com.capacity.ui.R;
import com.capacity.ui.core.BaseFragment;
import com.capacity.ui.login.entity.UserInfo;
import com.capacity.ui.login.model.LoginModel;
import com.capacity.ui.main.ui.MainActivity;
import com.capacity.widget.CustomToast;
import com.capacity.widget.TitleBar;
import com.qdaily.frame.core.RxSubscriber;
import com.qdaily.frame.managercenter.MManagerCenter;
import com.qdaily.network.UrlApi;

import butterknife.Bind;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class LoginFragment extends BaseFragment {

    @Bind(R.id.edit_name)
    EditText mNameEdit;
    @Bind(R.id.edit_password)
    EditText mPasswordEdit;
    @Bind(R.id.edit_net)
    EditText mNetEdit;

    private LoginModel mLoginModel;

    @Override
    protected int getLayoutId() {
        return R.layout.login_fragment_layout;
    }

    @Override
    protected void initData() {
        mLoginModel = new LoginModel(mActivity);
    }

    @Override
    protected void initView() {
        mActivity.setTitleRightTxt(mActivity.getString(R.string.login_title));
        mNetEdit.setText(UrlApi.getCurrentUrl());
        mActivity.setTitleRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {

                String net = mNetEdit.getText().toString();
                if (!TextUtils.isEmpty(net)) {
                    UrlApi.setCurrentUrl(net);
                } else {
                    UrlApi.initUrl();
                }
                String name = mNameEdit.getText().toString();
                String password = mPasswordEdit.getText().toString();
                mLoginModel.login(name, password, new RxSubscriber<UserInfo>() {
                    @Override
                    protected void onNetStart() {
                        mActivity.showLoadingView();
                    }

                    @Override
                    protected void onSuccess(UserInfo userInfo) {
                        if (userInfo == null) {
                            onFail("数据错误");
                            return;
                        }
                        if (MManagerCenter.getManager(UserInfoManager.class).updateUserInfo(userInfo)) {
                            startActivity(MainActivity.newInstance(mActivity));
                            mActivity.finish();
                        } else {
                            onFail("更新用户信息失败");
                        }

                    }

                    @Override
                    protected void onFail(String msg) {
                        CustomToast.makeText(msg).show();
                    }

                    @Override
                    protected void onFinish() {
                        mActivity.dismissLoadingView();
                    }
                });
            }
        });
    }
}
