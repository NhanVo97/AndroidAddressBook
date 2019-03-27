package com.example.qlsll.API.Service;

import com.example.qlsll.API.RetrofitResfulAPI;
import com.example.qlsll.Utils.Constant;

public class APIBaseService {
    public static UserService getUserAPIService() {
       return RetrofitResfulAPI.getInstance().create(UserService.class);
    }
    public static AuthService getAuthAPIService() {
        return RetrofitResfulAPI.getInstance().create(AuthService.class);
    }
}