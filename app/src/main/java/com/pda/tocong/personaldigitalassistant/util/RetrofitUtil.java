package com.pda.tocong.personaldigitalassistant.util;


import com.pda.tocong.personaldigitalassistant.service.RetrofitService;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;

/**
 * Created by 陶聪
 * 创作时间: 2016-09-22.16:28
 * 该类的作用:
 * 公司：上海家乐宝真好电子商务公司
 */
public class RetrofitUtil {
    private static RetrofitUtil mInstance;
    private OkHttpClient mOkHttpClient;
    Retrofit mRetrofit;
    RetrofitService mRetrofitService;
    HttpLoggingInterceptor mHttpLoggingInterceptor;

    private RetrofitUtil() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        mOkHttpClient = new OkHttpClient()
                .newBuilder().cookieJar(new JavaNetCookieJar(cookieManager))
                .addInterceptor(mHttpLoggingInterceptor)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(Constants.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mRetrofitService = mRetrofit.create(RetrofitService.class);
    }

    public static RetrofitUtil getmInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtil();
                }
            }
        }
        return mInstance;
    }

    public static Observable<String> asynPost(String method, Map<String, String> params) {
        return getmInstance()._asynPost(method,params);
    }

    private Observable<String> _asynPost(String method,Map<String, String> params) {
        return mRetrofitService.getData(method,params);
    }

}
