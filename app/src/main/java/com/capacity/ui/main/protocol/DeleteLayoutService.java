package com.capacity.ui.main.protocol;

import com.qdaily.network.entity.Result;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelinghui on 17/9/14.
 */

public interface DeleteLayoutService {

    @GET("deletelayout")
    Observable<Result<Void>> deleteLayout(@Query("commandCode")String commandCode,@Query("userId")String userId,@Query("layoutId")String layoutId);
}
