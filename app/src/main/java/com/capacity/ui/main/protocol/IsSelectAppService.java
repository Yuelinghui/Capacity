package com.capacity.ui.main.protocol;

import com.qdaily.network.entity.Result;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelinghui on 17/9/13.
 */

public interface IsSelectAppService {

    @GET("isappselect")
    Observable<Result<Boolean>> isAppSelect(@Query("commandCode")String commandCode,@Query("userId")String userId,@Query("appId")String appId);
}
