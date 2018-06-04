package com.capacity.ui.main.protocol;

import com.capacity.ui.main.entity.AppSortInfo;
import com.qdaily.network.entity.Result;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelinghui on 17/9/7.
 */

public interface SceneListService {
    @GET("apptask")
    Observable<Result<List<AppSortInfo>>> getAppSort(@Query("commandCode")String commandCode);
}
