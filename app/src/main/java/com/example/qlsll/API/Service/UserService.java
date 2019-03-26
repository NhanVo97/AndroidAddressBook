package com.example.qlsll.API.Service;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.PageRequest;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.example.qlsll.API.RetrofitResfulAPI;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @POST("user/signup")
    Observable<APIResponse> signUpUser(@Body JsonObject jsonObject);
    @GET("admin/profile")
    Observable<APIResponse> getAdminProfile(@Header("X-Access-Token") String token);
    @POST("admin/user/list")
    Observable<APIResponse> getListUserByAdmin(@Header("X-Access-Token") String token,@Body PageRequest pageRequest);
    @PUT("admin/user/{id}")
    Observable<APIResponse> updateUserByAdmin(@Header("X-Access-Token") String token, @Body UserRequest userRequest, @Path("id") String id);
    @DELETE("admin/user")
    Observable<APIResponse> deleteUserByAdmin(@Header("X-Access-Token") String token, @Query("user_ids") String user_ids);
}
