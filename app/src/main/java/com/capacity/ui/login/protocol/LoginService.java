package com.capacity.ui.login.protocol;

import com.capacity.ui.login.entity.UserInfo;
import com.qdaily.network.entity.Result;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelinghui on 17/8/31.
 */

public interface LoginService {
    @GET("login")
    Observable<Result<UserInfo>> login(@Query("commandCode") String commandCode, @Query("role") String role, @Query("userId") String userId, @Query("password") String password);
}
