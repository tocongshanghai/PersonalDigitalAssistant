package com.pda.tocong.personaldigitalassistant.service;


import com.pda.tocong.personaldigitalassistant.util.Constants;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 陶聪
 * 创作时间: 2016-09-22.16:30
 * 该类的作用:
 * 公司：上海家乐宝真好电子商务公司
 */
public interface RetrofitService {

    @FormUrlEncoded
    @POST(Constants.IF_mgr_food+"{method}")
    Observable<String> getData(@Path("method") String method, @FieldMap(encoded = true) Map<String, String> params);

}
