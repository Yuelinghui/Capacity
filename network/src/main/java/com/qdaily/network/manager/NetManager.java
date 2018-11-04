package com.qdaily.network.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qdaily.network.UrlApi;
import com.qdaily.network.interceptor.AddCookiesInterceptor;
import com.qdaily.network.interceptor.ReceivedCookiesInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yuelinghui on 17/8/1.
 */

public class NetManager {
    private static NetManager mNetManager = null;
    private static Retrofit mRetrofit = null;
    private static OkHttpClient mOkHttpClient;

    private NetManager() {
        mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor()).build();
        Gson gson = new GsonBuilder().setLenient().create();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .baseUrl(UrlApi.getCurrentUrl()).build();
    }

    public synchronized static NetManager getInstance() {
        if (mNetManager == null) {
            mNetManager = new NetManager();
        }
        return mNetManager;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

}
