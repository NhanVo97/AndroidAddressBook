package com.example.qlsll.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.UserService;
import com.example.qlsll.R;
import com.example.qlsll.Utils.CommonUtil;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.MD5Hash;
import com.example.qlsll.Utils.Response;
import com.google.gson.JsonObject;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class Resgister extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText edFirstName,edLastName,edEmail,edPhone,edAddress,edPassword,edCfPassword,edBirthDay;
    Button btnSignUp;
    TextView tvLogin,tvForgotPassword;
    UserService userService;
    int day,month,year;
    private boolean validateInput(
            String firstName,String lastName,String email,String phone
            ,String password, String cfpassword
            )
    {
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
               password.isEmpty() || cfpassword.isEmpty())
       {
           Response.toastError(getApplicationContext(),"Không được bỏ trống Input", Constant.TOASTSORT);
           return false;
       }
       else
       {
            // check email
           if(!CommonUtil.isEmailFormat(email))
           {
               Response.toastError(getApplicationContext(),"Email không hợp lệ",Constant.TOASTSORT);
               return false;
           }
           // check phone if have
           if(!phone.isEmpty())
           {
               if(!CommonUtil.isPhoneNumberFormat(phone))
               {
                   Response.toastError(getApplicationContext(),"Phone không hợp lệ",Constant.TOASTSORT);
                   return false;
               }
           }
           // password not matches
           if(!password.equals(cfpassword))
           {
               Response.toastError(getApplicationContext(),"Password không trùng",Constant.TOASTSORT);
               return false;
           }

       }
       return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        // Anh Xa
            intitData();
        // Event
        btnSignUp.setOnClickListener(this);
        edBirthDay.setOnClickListener(this);
    }
    private void intitData()
    {
        edFirstName = findViewById(R.id.firstName);
        edLastName = findViewById(R.id.lastName);
        edEmail = findViewById(R.id.email);
        edPhone = findViewById(R.id.phone);
        edAddress = findViewById(R.id.address);
        edPassword = findViewById(R.id.password);
        edCfPassword = findViewById(R.id.cfpassword);
        edBirthDay = findViewById(R.id.birthday);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.btnLogin);
        userService = APIBaseService.getUserAPIService();
    }
    @Override
    public void onClick(View v) {
        int idCase = v.getId();
        switch (idCase)
        {
            case R.id.btnSignUp:

                String firstName = edFirstName.getText().toString();
                String lastName = edLastName.getText().toString();
                String email = edEmail.getText().toString();
                String phone = edPhone.getText().toString();
                String password = edPassword.getText().toString();
                String cfpassword = edCfPassword.getText().toString();
                String address = edAddress.getText().toString();
                String birthDay = edBirthDay.getText().toString();
                if(validateInput(firstName,lastName,email,phone,password,cfpassword))
                {   String passwordHash="";
                    try {
                        passwordHash = MD5Hash.MD5Encrypt(password);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    Date birthday = new Date();

                    if(!birthDay.isEmpty())
                    {
                        birthday = new Date(edBirthDay.getText().toString());
                    }
//                    // UserRequest userRequest = new UserRequest(email,passwordHash,firstName,lastName,phone,address,birthday);
//                    userRequest.setLang("EN");
//                    sendToServer(userRequest);
                }

                break;
            case R.id.birthday :
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog dialog = new DatePickerDialog(this, this,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
        }


    }
    private void sendToServer(UserRequest userRequest)
    {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lastName",userRequest.getLastName());
        jsonObject.addProperty("firstName", userRequest.getFirstName());
        jsonObject.addProperty("phone",userRequest.getPhone());
        jsonObject.addProperty("email",userRequest.getEmail());
        jsonObject.addProperty("dob",year+"-"+month+"-"+day);
        jsonObject.addProperty("address",userRequest.getAddress());
        jsonObject.addProperty("lang", userRequest.getDob().toString());
        jsonObject.addProperty("passwordHash",userRequest.getPasswordHash());
        userService.signUpUser(jsonObject).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<APIResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(APIResponse apiResponse) {
                Toast.makeText(Resgister.this, apiResponse.getData()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(Resgister.this, e+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(Resgister.this, "TC", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        edBirthDay.setText(new StringBuilder()
                .append(dayOfMonth)
                .append("/").append(month + 1)
                .append("/").append(year).append(" "));
        this.day = dayOfMonth;
        this.month = month;
        this.year = year;
    }





    }


