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
    Observable<APIResponse> signUpUser(@Body UserRequest userRequest);
    @GET("user/profile")
    Observable<APIResponse> getProfileUser(@Header("X-Access-Token") String accessToken);

}
