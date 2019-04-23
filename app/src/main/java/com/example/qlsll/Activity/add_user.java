package com.example.qlsll.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.UserService;
import com.example.qlsll.R;
import com.example.qlsll.Utils.CommonUtil;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.MD5Hash;
import com.example.qlsll.Utils.Response;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class add_user extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText edFirstName,edLastName,edEmail,edPhone,edAddress,edPassword,edCfPassword,edBirthDay;
    Button btnSignUp;
    TextView tvLogin,tvForgotPassword;
    UserService userService;
    String day,month,year;
    APIResponse apiResponse;
    private boolean validateInput(
            String firstName,String lastName,String email,String phone
            ,String password, String cfpassword
    )
    {
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || cfpassword.isEmpty())
        {
            Response.toastError(getApplicationContext(),getResources().getString(R.string.err_input_invalid), Constant.TOASTSORT);
            return false;
        }
        else
        {
            // check email
            if(!CommonUtil.isEmailFormat(email))
            {
                Response.toastError(getApplicationContext(),getResources().getString(R.string.err_email_invalid),Constant.TOASTSORT);
                return false;
            }
            // check phone if have
            if(!phone.isEmpty())
            {
                if(!CommonUtil.isPhoneNumberFormat(phone))
                {
                    Response.toastError(getApplicationContext(),getResources().getString(R.string.err_phone_invalid),Constant.TOASTSORT);
                    return false;
                }
            }
            // password not matches
            if(!password.equals(cfpassword))
            {
                Response.toastError(getApplicationContext(),getResources().getString(R.string.err_the_same_password),Constant.TOASTSORT);
                return false;
            }

        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        // Anh Xa
        intitData();
        // Event
        btnSignUp.setOnClickListener(this);
        edBirthDay.setOnClickListener(this);
    }
    private void intitData()
    {
        edFirstName = findViewById(R.id.adduser_firstName);
        edLastName = findViewById(R.id.adduser_lastName);
        edEmail = findViewById(R.id.adduser_email);
        edPhone = findViewById(R.id.adduser_phone);
        edAddress = findViewById(R.id.adduser_address);
        edPassword = findViewById(R.id.adduser_password);
        edCfPassword = findViewById(R.id.adduser_cfpassword);
        edBirthDay = findViewById(R.id.adduser_birthday);
        btnSignUp = findViewById(R.id.adduser_btnSignUp);
        userService = APIBaseService.getUserAPIService();
    }
    @Override
    public void onClick(View v) {
        int idCase = v.getId();
        switch (idCase)
        {
            case R.id.adduser_btnSignUp:

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
                    String dob = year+"-"+month+"-"+day;
                    String lang = "En";
                    UserRequest userRequest = new UserRequest(lastName,firstName,phone,email,dob,address,lang,passwordHash);
                    sendToServer(userRequest);
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

        userService.signUpUser(userRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<APIResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(APIResponse res) {
                apiResponse = res;
            }

            @Override
            public void onError(Throwable e) {
                Response.toastError(add_user.this, getResources().getString(R.string.register_error),Constant.TOASTSORT);
                Log.e("APIREGISTERUSER",e.toString());
            }

            @Override
            public void onComplete() {
                if(apiResponse.getStatus() == APIStatus.OK.getCode())
                    Response.toastSuccess(add_user.this, getResources().getString(R.string.register_success),Constant.TOASTSORT);
                else
                    Response.APIToastError(add_user.this, apiResponse.getStatus(),Constant.TOASTSORT);
            }
        });


    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        this.day = String.valueOf(dayOfMonth);
        this.month = month <10 ? "0"+(month+1): String.valueOf(month);
        this.year = String.valueOf(year);
        edBirthDay.setText(new StringBuilder()
                .append(dayOfMonth)
                .append("/").append(month + 1)
                .append("/").append(year).append(" "));
    }
}

