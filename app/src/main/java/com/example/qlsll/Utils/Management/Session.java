package com.example.qlsll.Utils.Management;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.UserService;
import com.example.qlsll.Activity.MainActivity;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.Response;
import com.google.gson.Gson;

import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Session {
    private Context mContext;
    private  String accessToken;
    private  APIResponse apiResponse;
    private SharedPreferences sharedPreferences;
    public Session(String accessToken,Context mContext) {
        this.mContext = mContext;
        this.accessToken = accessToken;
        sharedPreferences = mContext.getSharedPreferences("User",Context.MODE_PRIVATE);
    }
    public boolean initUser() {

        if(!accessToken.isEmpty())
        {
            UserService userService = APIBaseService.getUserAPIService();
            userService.getProfileUser(accessToken)
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
                            Response.toastError(mContext,"Fail to call API "+e.toString(), Constant.TOASTSORT);
                            Log.e("APIINITUSER",e.toString());
                        }

                        @Override
                        public void onComplete() {
                            if(apiResponse.getStatus() == APIStatus.OK.getCode())
                            {
                                UserResponse userResponse = new Gson().fromJson(new Gson().toJson((apiResponse.getData())), (Type) UserResponse.class);
//                                SharedPreferences sharedPreferences = mContext.getSharedPreferences("User", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(userResponse);
                                editor.putString("User",json);
                                editor.putString("Token",accessToken);
                                editor.commit();
                            }
                            else
                            {
                                Response.APIToastError(mContext, apiResponse.getStatus(), Constant.TOASTSORT);
                                // clear all session & backhome
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear().commit();
                            }
                        }
                    });
            if(sharedPreferences!=null){
                return true;
            }
        }
        return false;
    }


}
