package com.capacity.ui.main.protocol;

import com.qdaily.network.entity.Result;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelinghui on 17/9/14.
 */

public interface UpdateLayoutService {

    @POST("layout")
    Observable<Result<Void>> updateLayout(@Query("commandCode") String commandCode, @Body RequestBody body);
}
