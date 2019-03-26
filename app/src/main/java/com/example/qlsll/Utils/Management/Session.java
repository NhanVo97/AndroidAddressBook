package com.example.qlsll.Utils.Management;
import android.content.Context;
import android.util.Log;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Response.AdminResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.UserService;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.Response;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Session {
    private Context mContext;
    private String accessToken;
    private APIResponse apiResponse;
     static UserResponse userResponse;
    public Session(String accessToken,Context mContext) {
        this.mContext = mContext;
        this.accessToken = accessToken;
    }
    public static UserResponse getUserResponse() {
        return userResponse;
    }

    public static void setUserResponse(UserResponse userResponse) {
        Session.userResponse = userResponse;
    }
    public void initUser()
    {
        if(!accessToken.isEmpty())
        {
            UserService userService = APIBaseService.getUserAPIService();
            userService.getAdminProfile(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<APIResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(APIResponse res) {
                            apiResponse = res;
                        }

                        @Override
                        public void onError(Throwable e) {
                            Response.toastError(mContext,"Fail to call API "+e.toString(),Constant.TOASTSORT);
                        }

                        @Override
                        public void onComplete() {
                            if(apiResponse.getStatus() == APIStatus.OK.getCode())
                            {
                                Session.setUserResponse(new Gson().fromJson(new Gson().toJson((apiResponse.getData())), (Type) UserResponse.class));
                            }
                            else
                            {
                                Response.APIToastError(mContext,apiResponse.getStatus(),Constant.TOASTSORT);
                            }
                        }
                    });
        }
    }
}
