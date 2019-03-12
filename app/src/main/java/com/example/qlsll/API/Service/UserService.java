package com.example.qlsll.API.Service;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.UserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @POST("user/signup")
    Call<APIResponse> saveUser(@Body UserRequest userRequest);
    @GET("user/{idUser}")
    Call<APIResponse> getUserByID(@Path("idUser") String idUser);
    @PUT("user")
    Call<APIResponse> updateUser(@Body UserRequest userRequest);
}
