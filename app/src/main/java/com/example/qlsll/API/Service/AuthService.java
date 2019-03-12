package com.example.qlsll.API.Service;

import com.example.qlsll.API.Model.APIResponse;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Observable<APIResponse> LoginUser(@Body JsonObject object);
}
