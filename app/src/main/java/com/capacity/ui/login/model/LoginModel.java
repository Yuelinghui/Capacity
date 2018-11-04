package com.capacity.ui.login.model;

import android.content.Context;

import com.capacity.ui.core.CommandCode;
import com.capacity.ui.core.Role;
import com.capacity.ui.login.entity.UserInfo;
import com.capacity.ui.login.protocol.LoginService;
import com.qdaily.frame.core.RxSubscriber;
import com.qdaily.network.NetModel;
import com.qdaily.network.manager.NetManager;
import com.qdaily.network.manager.RxManager;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class LoginModel extends NetModel {

    public LoginModel(Context context) {
        super(context);
    }

    public void login(String name, String password, RxSubscriber<UserInfo> subscriber) {
        LoginService loginService = NetManager.getInstance().create(LoginService.class);
        RxManager.getInstance().doSubscribe(loginService.login(CommandCode.LOGIN, Role.USER,name, password), subscriber);
    }
}
