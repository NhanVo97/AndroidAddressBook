package com.example.qlsll.API.Service;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.AuthRequest;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Observable<APIResponse> authLogin(@Body AuthRequest authRequest);
    @GET("/auth/admin")
    Observable<APIResponse> initUser(@Header("X-Access-Token") String token);
    @GET("/auth/admin")
    Observable<APIResponse> initAdmin(@Header("X-Access-Token") String token);

}
