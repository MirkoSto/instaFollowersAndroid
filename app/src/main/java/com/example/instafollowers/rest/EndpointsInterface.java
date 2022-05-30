package com.example.instafollowers.rest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface EndpointsInterface {

    @GET("statistic")
    Call<HomeStatisticResponse> getData();

    @GET("follow")
    Call<ActionResponse> follow(@QueryMap Map<String, String> params);

    @GET("unfollow")
    Call<ActionResponse> unfollow();

    @GET("like")
    Call<ActionResponse> like(@QueryMap Map<String, String> params);


    @POST("login")
    Call<LoginRequest> login(@QueryMap Map<String, String> params);

    @GET("isLogged")
    Call<LoginResponse> isLogged();

    @GET("watch")
    Call<ActionResponse> watch();
}
