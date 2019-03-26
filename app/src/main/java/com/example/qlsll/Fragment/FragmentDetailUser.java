package com.example.qlsll.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.R;
import com.example.qlsll.Utils.CommonUtil;
public class FragmentDetailUser extends Fragment implements View.OnClickListener {
    View v;
    EditText edFirstName,edLastName,edEmail,edbirthday,edAddress,edPhone;
    Spinner spLanguage;
    TextView tvInfoOf,tvCreateDate,tvStatus;
    Button btnDelete,btnUpdate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_detailuser,container,false);
        UserResponse userResponse = (UserResponse) getArguments().getSerializable("User");
        // Anh Xa
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
        if(userResponse!=null)
        {
            // check if response null,return  ""
            userResponse.setFirstName(!userResponse.getFirstName().isEmpty() ? userResponse.getFirstName() : "");
            userResponse.setLastName(!userResponse.getLastName().isEmpty() ? userResponse.getLastName() : "");
            userResponse.setMailAddress(!userResponse.getMailAddress().isEmpty() ? userResponse.getMailAddress() : "");
            userResponse.setPhone(!userResponse.getPhone().isEmpty() ? userResponse.getPhone() : "");
            userResponse.setAddress(!userResponse.getAddress().isEmpty() ? userResponse.getAddress() : "");
            userResponse.setDob(!userResponse.getDob().isEmpty() ? userResponse.getDob() : "");
            userResponse.setSignupDate(!userResponse.getSignupDate().isEmpty() ? userResponse.getSignupDate() : "");
            // set value to display
            edFirstName.setText(userResponse.getFirstName());
            edLastName.setText(userResponse.getLastName());
            edEmail.setText(userResponse.getMailAddress());
            edPhone.setText(userResponse.getPhone());
            edAddress.setText(userResponse.getAddress());
            edbirthday.setText(userResponse.getDob());
            tvCreateDate.setText(userResponse.getSignupDate());
            tvInfoOf.setText(getResources().getString(R.string.informationOF) +" " + userResponse.getFirstName());
            tvStatus.setText(CommonUtil.getStatus(userResponse.getStatus()));
        }
        return v;
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
        }
    }

    private void handleDeleteUser() {

    }
    private void handleUpdateUser() {

    }
}
