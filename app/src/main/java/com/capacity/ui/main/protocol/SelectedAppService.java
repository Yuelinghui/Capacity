package com.capacity.ui.main.protocol;

import com.capacity.ui.main.entity.AppInfo;
import com.qdaily.network.entity.Result;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelinghui on 17/9/13.
 */

public interface SelectedAppService {

    @GET("selectedapp")
    Observable<Result<List<AppInfo>>> getSelectedApp(@Query("commandCode")String commandCode,@Query("userId")String userId);
}
