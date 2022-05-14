package com.example.instafollowers.rest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface EndpointsInterface {

    @GET("statistic")
    Call<HomeStatisticResponse> getData();

    @GET("follow")
    Call<StartFollowingResponse> startFollowing(@QueryMap Map<String, String> params);

    @POST("login")
    Call<LoginRequest> login(@QueryMap Map<String, String> params);

    @GET("isLoged")
    Call<LoginResponse> isLogged();
}
