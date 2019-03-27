package com.example.qlsll.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.qlsll.API.APIStatus;

public class Response {
    public static void toastError(Context context, String stringToast, int timeShow)
    {
        Toast.makeText(context,stringToast,timeShow).show();
    }
    public static void toastSuccess(Context context, String stringToast, int timeShow)
    {
        Toast.makeText(context,stringToast,timeShow).show();
    }
    public static void APIToastError(Context context,int status, int timeShow)
    {
        String errorStatus = "";
        for(APIStatus api : APIStatus.values()) {
            if(api.getCode() == status)
            {
                errorStatus = api.getDescription();
            }
        }
        Toast.makeText(context,errorStatus,timeShow).show();
        Log.e("API_ERR_"+status,errorStatus);

    }
}
