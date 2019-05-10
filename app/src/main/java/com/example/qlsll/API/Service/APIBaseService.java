package com.example.qlsll.API.Service;

import com.example.qlsll.API.RetrofitResfulAPI;
import com.example.qlsll.Utils.Constant;

public class APIBaseService {
    public static UserService getUserAPIService() {
       return RetrofitResfulAPI.getInstance().create(UserService.class);// khởi tạo severvice
    }
    public static AuthService getAuthAPIService() {
        return RetrofitResfulAPI.getInstance().create(AuthService.class);
    }
    public static AdminService getAdminAPIService() {
        return RetrofitResfulAPI.getInstance().create(AdminService.class);
    }
    public static AddressBookService getAddressBookAPIService() {
        return RetrofitResfulAPI.getInstance().create(AddressBookService.class);
    }
    public static GroupAddressBookService getGroupAddressBookAPIService() {
        return RetrofitResfulAPI.getInstance().create(GroupAddressBookService.class);
    }
}