package com.example.instafollowers.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //private static final String BASE_URL = "http://192.168.1.43:5000/";
    private static final String BASE_URL = "http://192.168.1.8:5000/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
