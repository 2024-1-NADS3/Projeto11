package com.example.treinarai.retrofitUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static Retrofit instanceRetrofit(){
        return new Retrofit.Builder().baseUrl(
                "https://api-treina-ai.vercel.app/"
        ).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ServiceApi intanceService(Retrofit retrofit){
        return retrofit.create(ServiceApi.class);
    }
}
