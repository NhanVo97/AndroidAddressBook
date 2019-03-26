package com.example.qlsll.API;
import com.example.qlsll.API.Service.AuthService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.Nullable;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitResfulAPI {
    private static AuthService authService;
    private static String BASE_URL = "http://192.168.43.152:8080/api/v1/"; // API Config
    private static Retrofit INSTANCE = null;
    private static Gson gson;
    public static Retrofit getInstance() {
        if (INSTANCE == null) {

            gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient okClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Response response = chain.proceed(chain.request());
                                    return response;
                                }
                            })
                    .build();


            INSTANCE = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .client(okClient)
                    .build();
        }
        return INSTANCE;
    }


}
