package com.example.qlsll.Utils.Management;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Response.AdminResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AdminService;
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
    private static APIResponse apiResponse;
    public Session(String accessToken,Context mContext) {
        this.mContext = mContext;
        this.accessToken = accessToken;
    }
    public static void initAdmin(Context mContext,String accessToken) {

        if(!accessToken.isEmpty())
        {
            AdminService adminService = APIBaseService.getAdminAPIService();
            adminService.getAdminProfile(accessToken)
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
                            Log.e("APIINITADMIN",e.toString());

                        }

                        @Override
                        public void onComplete() {
                            if(apiResponse.getStatus() == APIStatus.OK.getCode())
                            {
                                AdminResponse adminResponse = new Gson().fromJson(new Gson().toJson((apiResponse.getData())), (Type) AdminResponse.class);
                                SharedPreferences sharedPreferences = mContext.getSharedPreferences("Admin", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(adminResponse);
                                editor.putString("Admin",json);
                                editor.commit();
                            }
                            else
                            {
                                Response.APIToastError(mContext, apiResponse.getStatus(), Constant.TOASTSORT);
                            }
                        }
                    });
        }
    }
//    public static boolean checkToken(String token)
//    {
//
//    }

}
