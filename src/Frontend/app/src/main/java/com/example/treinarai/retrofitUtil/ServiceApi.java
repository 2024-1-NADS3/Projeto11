package com.example.treinarai.retrofitUtil;

import com.example.treinarai.model.ResponseModel;
import com.example.treinarai.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ServiceApi {

    @POST("logar/user")
    Call<ResponseModel> logarUser(@Body UserModel user);

    @POST("criar/user")
    Call<ResponseModel> criarUser(@Body UserModel user);

    @PUT("atualizar/user")
    Call<ResponseModel> atualizarUser(@Body UserModel user);

}
