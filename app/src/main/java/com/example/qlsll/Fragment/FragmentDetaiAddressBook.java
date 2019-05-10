package com.example.qlsll.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.AddressBookRequest;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.example.qlsll.API.Model.Response.AddressBookResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AddressBookService;
import com.example.qlsll.API.Service.AdminService;
import com.example.qlsll.Activity.ManagementActivity;
import com.example.qlsll.Activity.add_addressbook;
import com.example.qlsll.Adapter.SpinnerCountryAdapter;
import com.example.qlsll.Model.Country;
import com.example.qlsll.R;
import com.example.qlsll.Utils.CommonUtil;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.DateUtils;
import com.example.qlsll.Utils.Response;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class FragmentDetaiAddressBook extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener,UpdateAPI  {
    View v;
    private Button btn_capnhat;
    private EditText ed_ho;
    private EditText ed_ten;
    private EditText ed_mail;
    private EditText ed_sdt;
    private EditText ed_congty;
    AddressBookResponse currentResponse;
    String accessToken = "";
    UpdateAPI updateAPI;
    FragmentManager fragmentManager;
     APIResponse apiResponse;
    AddressBookService addressBookService;

    boolean isCalling = false;

    private boolean checkError(String addHo, String addTen, String addMail, String addSdt, String addCongty) {
        if (addHo.isEmpty() || addTen.isEmpty() || addMail.isEmpty()) {
            Response.toastError(getContext(), "Nhập đủ thông tin", Constant.TOASTSORT);
            return false;
        } else {
            if (!CommonUtil.isEmailFormat(addMail)) {
                Response.toastError(getContext(), "Nhập lại Email", Constant.TOASTSORT);
                return false;
            }
            if (!addSdt.isEmpty()) {
                if (!CommonUtil.isPhoneNumberFormat(addSdt)) {
                    Response.toastError(getContext(), "Nhập lại số điện thoại", Constant.TOASTSORT);
                    return false;
                }
            }
        }
        return true;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_detaiaddressbook, container, false);
        AnhXa();

       return v;
    }
    private void AnhXa() {
        ed_ho = v.findViewById(R.id.ed_firstName);
        ed_ten = v.findViewById(R.id.ed_lastName);
        ed_mail = v.findViewById(R.id.ed_email);
        ed_sdt = v.findViewById(R.id.ed_phone);
        ed_congty = v.findViewById(R.id.ed_company);
        btn_capnhat = v.findViewById(R.id.btn_caddread);
        fragmentManager = getFragmentManager();
        accessToken = getArguments().getString("accessToken");
        updateAPI = (UpdateAPI) getContext();

        currentResponse = (AddressBookResponse) getArguments().getSerializable("AddressBook");
        Log.d("CURENT",currentResponse.getAddressBookId()+"");

        initDataAddressBookDetail(currentResponse);
        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abc","nhan vao");
                handleUpdateRessBook();
            }
        });
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_caddread:
                    Log.d("abc","nhan vao");
               // handleUpdateRessBook();
                break;

        }
    }
    private void initDataAddressBookDetail(AddressBookResponse currentResponse) {

        if (currentResponse != null) {
            // check if response null,return  ""
            currentResponse.setFirstName(currentResponse.getFirstName() != null ? currentResponse.getFirstName() : "");
            currentResponse.setLastName(currentResponse.getLastName() != null ? currentResponse.getLastName() : "");
            currentResponse.setEmail(currentResponse.getEmail() != null ? currentResponse.getEmail() : "");
            currentResponse.setPhone(currentResponse.getPhone() != null ? currentResponse.getPhone() : "");
            currentResponse.setCompany(currentResponse.getCompany() != null ? currentResponse.getCompany() : "");


            // set value to display
            ed_ho.setText(currentResponse.getFirstName());
            ed_ten.setText(currentResponse.getLastName());
            ed_mail.setText(currentResponse.getEmail());
            ed_sdt.setText(currentResponse.getPhone());
            ed_congty.setText(currentResponse.getCompany());


        }
    }

    private boolean isValidateInput()
    {
        String firstName = ed_ten.getText().toString();
        String lastName = ed_ho.getText().toString();
        if(firstName.isEmpty() || lastName.isEmpty() )
        {
            return false;
        }
        return true;
    }
    private void handleUpdateRessBook() {
        Log.d("click","vao");
        if(isValidateInput())
        {
            if(isCalling){
                return;
            }
            isCalling = true;
            AddressBookRequest addressBookRequest = new AddressBookRequest();
            addressBookRequest.setAddressbookId(currentResponse.getAddressBookId());
            addressBookRequest.setFirstName(ed_ho.getText().toString());
            addressBookRequest.setLastName(ed_ten.getText().toString());
            addressBookRequest.setEmail(ed_mail.getText().toString());
            addressBookRequest.setPhone(ed_sdt.getText().toString());
            addressBookRequest.setCompany(ed_congty.getText().toString());
            Log.e("AAA",accessToken);
            addressBookService = APIBaseService.getAddressBookAPIService();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    addressBookService.updateAddressBook(accessToken,addressBookRequest)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<APIResponse>()  {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(APIResponse res) {
                                    apiResponse = res;

                                }

                                @Override
                                public void onError(Throwable e) {
//                            Response.toastError(getContext(),getResources().getString(R.string.update_error),Constant.TOASTSORT);
                                    Log.e("API_UPDATE_AddBook",e.toString());
                                }

                                @Override
                                public void onComplete() {

                                    Log.e("API_UPDATE_AddBook","thanh cong");

                                    if(apiResponse.getStatus() == APIStatus.OK.getCode()) {
                                        Response.toastSuccess(getContext(), getResources().getString(R.string.update_success), Constant.TOASTSORT);
                                        fragmentManager.popBackStack();

                                        updateAPI.checkUpdate(true);
                                    }
                                    else
                                        Response.APIToastError(getContext(),apiResponse.getStatus(),Constant.TOASTSORT);



                                }

                            });
                }
            });
            thread.start();

        }
        else
        {
            Response.toastError(getContext(),getResources().getString(R.string.err_input_invalid), Constant.TOASTSORT);
        }
    }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        @Override
        public void checkUpdate(boolean isCheck) {

        }
    }