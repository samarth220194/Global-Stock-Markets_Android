package com.global.market;

import retrofit2.http.GET;
import retrofit2.Call;

/**
 * Created by Samarth on 01-Jul-16.
 */
public interface jsonrequest {
        @GET("appu_android_app/global_stock_app/stock_list.php?continent=Asia")
        Call<JsonResponse> getJSON();
        @GET("appu_android_app/global_stock_app/stock_list.php?continent=Australia")
        Call<JsonResponse> getJSON1();
        @GET("appu_android_app/global_stock_app/stock_list.php?continent=Europe")
        Call<JsonResponse> getJSON2();
        @GET("appu_android_app/global_stock_app/stock_list.php?continent=North America")
        Call<JsonResponse> getJSON3();
        @GET("appu_android_app/global_stock_app/stock_list.php?continent=South America")
        Call<JsonResponse> getJSON4();
        @GET("appu_android_app/global_stock_app/stock_list.php?continent=Africa")
        Call<JsonResponse> getJSON5();

}