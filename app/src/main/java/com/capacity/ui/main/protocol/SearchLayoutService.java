package com.capacity.ui.main.protocol;

import com.capacity.ui.main.protocol.param.LayoutParam;
import com.qdaily.network.entity.Result;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelinghui on 17/9/14.
 */

public interface SearchLayoutService {
    @GET("deletelayout")
    Observable<Result<LayoutParam>> searchLayout(@Query("commandCode")String commandCode, @Query("userId") String userId);
}
