package com.example.qlsll.API.Service;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.AuthRequest;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/admin/login")
    Observable<APIResponse> AdminLogin(@Body AuthRequest authRequest);

}
