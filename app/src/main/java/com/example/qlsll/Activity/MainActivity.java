package com.example.qlsll.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.AuthRequest;
import com.example.qlsll.API.RetrofitResfulAPI;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AuthService;
import com.example.qlsll.R;
import com.example.qlsll.Utils.CommonUtil;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.MD5Hash;
import com.example.qlsll.Utils.Response;
import com.google.gson.JsonObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.functions.Action1;

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
        cbRemember = findViewById(R.id.cbRemember);
        // Check


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ettk.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Nhập Email", Toast.LENGTH_SHORT).show();
                }
                if(etpass.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }

                if(ettk.getText().toString().equals("")&&etpass.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Nhập thông tin", Toast.LENGTH_SHORT).show();
                }
                if (!ettk.getText().toString().equals("")&&!etpass.getText().toString().equals("")){
                    if(ettk.getText().toString().equals("sang1")&&etpass.getText().toString().equals("123")) {
                        Intent myIntent = new Intent(MainActivity.this, dsUser.class);
                        MainActivity.this.startActivity(myIntent);
                    }
                         if(ettk.getText().toString().equals("sang")&&etpass.getText().toString().equals("123")) {
                                 Intent myIntent = new Intent(MainActivity.this, DSActivity.class);
                                 MainActivity.this.startActivity(myIntent);
                         }
                        else{
                        Toast.makeText(MainActivity.this, "Thông tin sai", Toast.LENGTH_SHORT).show();
                    }
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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< .mine
                 Intent myIntent = new Intent(MainActivity.this, dangki.class);
=======
                Intent intent = new Intent(MainActivity.this,Register.class);
>>>>>>> .theirs
                MainActivity.this.startActivity(myIntent);
                startActivity(intent);
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
                apiResponseAuth = apiResponse;
                Response.toastSuccess(getApplicationContext(),apiResponse.getData().toString(),Constant.TOASTSORT);
            }

            @Override
            public void onError(Throwable e) {
               Response.toastSuccess(getApplicationContext(),e+"",Constant.TOASTSORT);
            }

            @Override
            public void onComplete() {

            }
        });
    }


}
