package com.example.qlsll.Fragment;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AdminService;
import com.example.qlsll.Activity.MainActivity;
import com.example.qlsll.Adapter.SpinnerCountryAdapter;
import com.example.qlsll.Model.Country;
import com.example.qlsll.R;
import com.example.qlsll.Utils.CommonUtil;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.DateUtils;
import com.example.qlsll.Utils.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class FragmentDetailUser extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener,UpdateAPI {
    View v;
    EditText edFirstName,edLastName,edEmail,edbirthday,edAddress,edPhone;

    TextView tvInfoOf,tvCreateDate,tvStatus;
    Button btnDelete,btnUpdate,btnLogOut;
    UserResponse currentResponse;
    String accessToken = "";
    UpdateAPI updateAPI;
    FragmentManager fragmentManager;
    Spinner spLanguage;
    Country country;
    List<Country> listCountry;
    APIResponse apiResponse;
        AdminService adminService;
    boolean isCalling = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_detailuser,container,false);
        // Anh Xa
        btnLogOut = v.findViewById(R.id.logout);
        edFirstName = v.findViewById(R.id.firstNameUser);
        edLastName = v.findViewById(R.id.lastNameUser);
        edEmail = v.findViewById(R.id.emailUser);
        edPhone = v.findViewById(R.id.phoneUser);
        edbirthday = v.findViewById(R.id.birthdayUser);
        edAddress = v.findViewById(R.id.addressUser);
        spLanguage = v.findViewById(R.id.spinnerLanguage);
        tvInfoOf = v.findViewById(R.id.detailUserName);
        tvCreateDate = v.findViewById(R.id.createDateUser);
        tvStatus = v.findViewById(R.id.statusUser);
        btnDelete = v.findViewById(R.id.deleteUser);
        btnUpdate = v.findViewById(R.id.updateUser);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        edbirthday.setOnClickListener(this);
        adminService = APIBaseService.getAdminAPIService();
        fragmentManager = getFragmentManager();
        accessToken = getArguments().getString("accessToken");
        updateAPI = (UpdateAPI) getContext();
        currentResponse = (UserResponse) getArguments().getSerializable("User");
        initDataUserDetail(currentResponse);
        Log.d("AAA",accessToken + " "+currentResponse.getFirstName());
        if(accessToken!=null){
            btnLogOut.setVisibility(View.INVISIBLE);
        }else {
            btnLogOut.setVisibility(View.VISIBLE);
        }
        return v;
    }

    private void initDataUserDetail(UserResponse currentResponse) {
        if(currentResponse!=null)
        {
            // check if response null,return  ""
            currentResponse.setFirstName(currentResponse.getFirstName()!=null? currentResponse.getFirstName() : "");
            currentResponse.setLastName(currentResponse.getLastName()!=null ? currentResponse.getLastName() : "");
            currentResponse.setMailAddress(currentResponse.getMailAddress()!=null ? currentResponse.getMailAddress() : "");
            currentResponse.setPhone(currentResponse.getPhone()!=null ? currentResponse.getPhone() : "");
            currentResponse.setAddress(currentResponse.getAddress()!=null ? currentResponse.getAddress() : "");
            currentResponse.setDob(currentResponse.getDob()!=null ? currentResponse.getDob() : "");
            currentResponse.setSignupDate(currentResponse.getSignupDate()!=null ? currentResponse.getSignupDate() : "");
            // set value to display
            edFirstName.setText(currentResponse.getFirstName());
            edLastName.setText(currentResponse.getLastName());
            edEmail.setText(currentResponse.getMailAddress());
            edPhone.setText(currentResponse.getPhone());
            edAddress.setText(currentResponse.getAddress());
            edbirthday.setText(DateUtils.formatDateString(currentResponse.getDob(),false));
            tvCreateDate.setText(DateUtils.formatDateString(currentResponse.getSignupDate(),true));
            tvInfoOf.setText(getResources().getString(R.string.informationOF) +" " + currentResponse.getFirstName()+" "+currentResponse.getLastName());
            tvStatus.setText(CommonUtil.getStatus(currentResponse.getStatus()));
            // set spinner value country language
            listCountry = new ArrayList<>();
            listCountry.add(new Country(R.drawable.en,"En","English"));
            listCountry.add(new Country(R.drawable.vn,"Vn","Việt Nam"));
            SpinnerCountryAdapter spinerCountryAdapter = new SpinnerCountryAdapter(getContext(),listCountry);
            spLanguage.setAdapter(spinerCountryAdapter);
            // check and select value language of user
            for(int i = 0; i<listCountry.size();i++)
            {
                if(listCountry.get(i).getKey().equals(currentResponse.getLang()))
                {
                    spLanguage.setSelection(i);
                    break;
                }
            }

        }
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User",MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {

            case R.id.deleteUser :
                handleDeleteUser();
                break;
            case R.id.updateUser:
                handleUpdateUser();
                break;
            case R.id.birthdayUser:
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog = new DatePickerDialog(getContext(), this,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
        }
    }

    private void handleDeleteUser() {
        adminService.deleteUserByAdmin(accessToken,currentResponse.getUserId())
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
                        Response.toastError(getContext(),getResources().getString(R.string.delete_error),Constant.TOASTSORT);
                        Log.e("API_DELETE_USER",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if(apiResponse.getStatus() == APIStatus.OK.getCode())
                        {
                            Response.toastSuccess(getContext(),getResources().getString(R.string.delete_success),Constant.TOASTSORT);
                            // go back
                            fragmentManager.popBackStack();
                            updateAPI.checkUpdate(true);
                        }
                        else
                            Response.APIToastError(getContext(),apiResponse.getStatus(),Constant.TOASTSORT);

                    }
                });

    }
    private boolean isValidateInput()
    {
        String firstName = edFirstName.getText().toString();
        String lastName = edLastName.getText().toString();
        if(firstName.isEmpty() || lastName.isEmpty() )
        {
            return false;
        }
        return true;
    }
    private void handleUpdateUser() {
        if(isValidateInput())
        {
            if(isCalling){
                return;
            }
            isCalling = true;
            UserRequest userRequest = new UserRequest();
            userRequest.setFirstName(edFirstName.getText().toString());
            userRequest.setLastName(edLastName.getText().toString());
            userRequest.setAddress(edAddress.getText().toString());
            userRequest.setPhone(edPhone.getText().toString());
            if(!currentResponse.getDob().equals(edbirthday.getText().toString()))
            {
                userRequest.setDob(DateUtils.convertToUTC(edbirthday.getText().toString()));
            }
            if(country!=null)
            {
                userRequest.setLang(country.getKey());
            }
            Log.e("AAA",accessToken);
            adminService.updateUserByAdmin(accessToken,userRequest,currentResponse.getUserId())
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
                            Response.toastError(getContext(),getResources().getString(R.string.update_error),Constant.TOASTSORT);
                            Log.e("API_UPDATE_USER",e.toString());
                        }

                        @Override
                        public void onComplete() {
                            if(apiResponse.getStatus() == APIStatus.OK.getCode()) {
                                Response.toastSuccess(getContext(), getResources().getString(R.string.update_success), Constant.TOASTSORT);
                                // go back
                                fragmentManager.popBackStack();
                                updateAPI.checkUpdate(true);
                            }
                            else
                                Response.APIToastError(getContext(),apiResponse.getStatus(),Constant.TOASTSORT);

                        }
                    });
        }
        else
        {
            Response.toastError(getContext(),getResources().getString(R.string.err_input_invalid), Constant.TOASTSORT);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        edbirthday.setText(new StringBuilder()
                .append(dayOfMonth)
                .append("-").append(month + 1)
                .append("-").append(year));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        country = listCountry.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void checkUpdate(boolean isCheck) {

    }

}
