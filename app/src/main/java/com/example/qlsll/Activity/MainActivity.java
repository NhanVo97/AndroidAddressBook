package com.example.qlsll.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.example.qlsll.API.Model.Response.SessionReponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AuthService;
import com.example.qlsll.API.Service.UserService;
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
    EditText edAccount ;
    EditText edPassword;
    CheckBox cbRemember;
    Button btnLogin,btnSignUp;
    boolean check;
    APIResponse apiResponseAuth;
    private boolean validateInput(String account,String password)
    {
        if(account.isEmpty() || password.isEmpty())
        {
            Response.toastError(getApplicationContext(),"Không được để trống Input", Constant.TOASTSORT);
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Anh Xa
        edAccount= findViewById(R.id.ettk);
        edPassword=findViewById(R.id.etpass);
        btnLogin = findViewById(R.id.btdn);
        btnSignUp = findViewById(R.id.btdk);
        cbRemember = findViewById(R.id.checkBox);
        // Check



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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("account",account);
        jsonObject.addProperty("passwordHash", MD5Hash.MD5Encrypt(password));
        jsonObject.addProperty("keepLogin",check);
        authService.LoginUser(jsonObject).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<APIResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(APIResponse apiResponse) {

                int status = apiResponse.getStatus();
                if(status == 200)
                {
                    apiResponseAuth = apiResponse;
                }
                else
                {
                    Response.toastError(getApplicationContext(),"Lỗi API",Constant.TOASTSORT);
                }

            }

            @Override
            public void onError(Throwable e) {
               Response.toastSuccess(getApplicationContext(),e+"",Constant.TOASTSORT);
            }

            @Override
            public void onComplete() {
//                Intent intent = new Intent(MainActivity.this, UserListActivity.class);
//                UserResponse userResponse = (UserResponse) apiResponseAuth.getData();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("user", (Serializable) userResponse);
//                intent.putExtra("bundleUser",bundle);
//                startActivity(intent);

            }
        });

    }
    private SessionReponse getSession(String idToken){
        final SessionReponse[] sessionReponse = {null};
        UserService userService = APIBaseService.getUserAPIService();
        userService.getSessionByAccessToken(idToken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<APIResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(APIResponse apiResponse) {
                String a = apiResponse.toString();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        return sessionReponse[0];
    }


}
