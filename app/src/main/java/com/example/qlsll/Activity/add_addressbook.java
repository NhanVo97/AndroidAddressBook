package com.example.qlsll.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.AddressBookRequest;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AddressBookService;
import com.example.qlsll.R;
import com.example.qlsll.Utils.CommonUtil;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.Response;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class add_addressbook extends AppCompatActivity {

    private Button btn_them;
    private EditText ed_ho;
    private EditText ed_ten;
    private EditText ed_mail;
    private EditText ed_sdt;
    private EditText ed_congty;
    private APIResponse apiResponse;

    private boolean checkError(String addHo, String addTen, String addMail, String addSdt, String addCongty) {
        if (addHo.isEmpty() || addTen.isEmpty() || addMail.isEmpty()) {
            Response.toastError(getApplicationContext(), "Nhập đủ thông tin", Constant.TOASTSORT);
            return false;
        } else {
            if (!CommonUtil.isEmailFormat(addMail)) {
                Response.toastError(getApplicationContext(), "Nhập lại Email", Constant.TOASTSORT);
                return false;
            }
            if (!addSdt.isEmpty()) {
                if (!CommonUtil.isPhoneNumberFormat(addSdt)) {
                    Response.toastError(getApplicationContext(), "Nhập lại số điện thoại", Constant.TOASTSORT);
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_addreadbook);
        AnhXa();
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addHo,addTen,addMail,addSdt,addCongty;
                addHo=ed_ho.getText().toString();
                addTen=ed_ten.getText().toString();
                addMail=ed_mail.getText().toString();
                addSdt=ed_sdt.getText().toString();
                addCongty=ed_congty.getText().toString();

                if(checkError(addHo,addTen,addMail,addSdt,addCongty)){
                    AddressBookRequest addressBookRequest=new AddressBookRequest(addHo,addTen,addMail,addSdt,addCongty);
                    SharedPreferences sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
                    String token = sharedPreferences.getString("Token","");
                    Log.e("aaa",token);
                    if(!token.isEmpty()){
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AddressBookService addressBookService = APIBaseService.getAddressBookAPIService();
                                addressBookService.addAddressBook(token,addressBookRequest)
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
                                        Log.e("API_ADD_ADDRESS",e.toString());
                                    }
                                    @Override
                                    public void onComplete() {
                                        if(apiResponse.getStatus() == APIStatus.OK.getCode()){
                                            Intent intent = new Intent(add_addressbook.this,ManagementActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Response.APIToastError(getApplicationContext(),apiResponse.getStatus(),Constant.TOASTSORT);
                                        }
                                    }
                                });
                            }
                        });
                        thread.start();

                    } else {
                        backToHome();
                    }
                }
            }
        });



    }
    private void AnhXa(){
        ed_ho=findViewById(R.id.ed_firstName);
        ed_ten=findViewById(R.id.ed_lastName);
        ed_mail=findViewById(R.id.ed_email);
        ed_sdt=findViewById(R.id.ed_phone);
        ed_congty=findViewById(R.id.ed_company);
        btn_them=findViewById(R.id.btn_addread); }
        private void backToHome(){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
}

