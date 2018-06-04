package com.capacity.ui.main.protocol;

import com.qdaily.network.entity.Result;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelinghui on 17/9/7.
 */

public interface SelectAppService {
    @GET("selectapp")
    Observable<Result<Void>> selectApp(@Query("commandCode")String commandCode,@Query("userId")String userId,@Query("appId")String appId);
}
