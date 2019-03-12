package com.example.qlsll.API.Service;

import com.example.qlsll.API.RetrofitResfulAPI;
import com.example.qlsll.Utils.Constant;

public class APIBaseService {
    public static UserService getUserAPIService() {
        return RetrofitResfulAPI.getClient(Constant.BASE_URL_API).create(UserService.class);
    }
}
