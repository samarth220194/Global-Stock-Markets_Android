package com.global.market;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Samarth on 05-Jul-16.
 */
public interface detailrequest {
    @GET
    Call<JsonResponse> DetailURL(@Url String url);
}
