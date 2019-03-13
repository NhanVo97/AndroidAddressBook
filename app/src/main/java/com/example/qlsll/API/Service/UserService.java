package com.example.qlsll.API.Service;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @POST("user/signup")
    Observable<APIResponse> signUpUser(@Body JsonObject jsonObject);
    @GET("user/{idUser}")
    Call<APIResponse> getUserByID(@Path("idUser") String idUser);
    @PUT("user")
    Call<APIResponse> saveUser(@Body UserRequest userRequest);
    @GET("session/{id}")
    Observable<APIResponse> getSessionByAccessToken(@Path("id") String idToken);
    @GET("admin/list")
    Observable<APIResponse> getListUser(@Body JsonObject jsonObject);
}
