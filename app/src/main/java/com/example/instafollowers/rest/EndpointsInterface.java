package com.example.instafollowers.rest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface EndpointsInterface {

    @GET("statistic")
    Call<HomeStatisticModel> getData();

    @GET("follow")
    Call<StartFollowingResponse> startFollowing(@QueryMap Map<String, String> params);
}
