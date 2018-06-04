package com.capacity.ui.main.protocol;

import com.capacity.ui.main.entity.AppInfo;
import com.qdaily.network.entity.Result;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelinghui on 17/9/7.
 */

public interface AppSortListService {
    @GET("appBykey")
    Observable<Result<List<AppInfo>>> getAppSortList(@Query("commandCode") String commandCode, @Query("feature") String feature
            , @Query("key") String key, @Query("userId") String userId);
}
