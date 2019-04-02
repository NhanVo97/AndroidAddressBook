package com.example.qlsll.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.AuthRequest;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.example.qlsll.API.Model.Response.SessionReponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AuthService;
import com.example.qlsll.API.Service.UserService;
import com.example.qlsll.Fragment.FragmentListUser;
import com.example.qlsll.R;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.MD5Hash;
import com.example.qlsll.Utils.Response;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    EditText edAccount;
    EditText edPassword;
    CheckBox cbRemember;
    Button btnLogin, btnSignUp;
    boolean check;
    APIResponse apiResponse;
    LinearLayout linearLogin,linearProcess;
    private boolean validateInput(String account, String password) {
        if (account.isEmpty() || password.isEmpty()) {
            Response.toastError(getApplicationContext(), getResources().getString(R.string.err_input_invalid), Constant.TOASTSORT);
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Anh Xa
        edAccount = findViewById(R.id.ettk);
        edPassword = findViewById(R.id.etpass);
        btnLogin = findViewById(R.id.btdn);
        btnSignUp = findViewById(R.id.btdk);
        cbRemember = findViewById(R.id.checkBox);
        linearLogin = findViewById(R.id.linearLayoutLogin);
        linearProcess = findViewById(R.id.linearProcess);
        // Check Data if have Token
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent myIntent = new Intent(MainActivity.this, Resgister.class);
                Intent intent = new Intent(MainActivity.this,Resgister.class);
                MainActivity.this.startActivity(myIntent);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = edAccount.getText().toString();
                String password = edPassword.getText().toString();
                check = cbRemember.isChecked() ? true : false;
                if(validateInput(account,password))
                {
                    try {
                        linearLogin.setAlpha((float) 0.6);
                        linearLogin.setEnabled(false);
                        linearProcess.setVisibility(View.VISIBLE);
                        handleLogin(account,password,check);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void handleLogin(String account, String password,boolean check) throws NoSuchAlgorithmException {
        AuthService authService = APIBaseService.getAuthAPIService();
        AuthRequest authRequest = new AuthRequest();
        authRequest.setAccount(account);
        authRequest.setPasswordHash(MD5Hash.MD5Encrypt(password));
        authRequest.setKeepLogin(check);
        authService.AdminLogin(authRequest)
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
                        Response.toastError(getApplicationContext(),getResources().getString(R.string.login_fail),Constant.TOASTSORT);
                        Log.e("API_LOGIN_ADMIN",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if(apiResponse.getStatus() == APIStatus.OK.getCode())
                        {
                            Intent intent = new Intent(MainActivity.this,AdminManagementActivity.class);
                            // send to activity
                            intent.putExtra("accessToken",apiResponse.getData().toString());
                            intent.putExtra("isAdmin",true);
                            startActivity(intent);
                        }
                        else
                        {
                            Response.APIToastError(getApplicationContext(),apiResponse.getStatus(),Constant.TOASTSORT);
                        }
                    }
                });

    }


}
