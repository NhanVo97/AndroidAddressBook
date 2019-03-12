package com.example.qlsll.Utils;

import android.content.Context;
import android.widget.Toast;

public class Response {
    public static void toastError(Context context, String stringToast, int timeShow)
    {
        Toast.makeText(context,stringToast,timeShow).show();
    }
    public static void toastSuccess(Context context, String stringToast, int timeShow)
    {
        Toast.makeText(context,stringToast,timeShow).show();
    }
}
